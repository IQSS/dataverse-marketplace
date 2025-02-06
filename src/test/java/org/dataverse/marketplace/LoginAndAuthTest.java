package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.dataverse.marketplace.model.Role;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.payload.auth.request.*;
import org.dataverse.marketplace.payload.auth.response.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginAndAuthTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();
    private String serverUrl;

    /**
     * Test for login, authentication and user management.
     * 
     * On this test:
     * 
     * /api/auth/login
     * /api/auth/signup
     * /api/auth/password/update
     * /api/auth/roles
     * /api/auth/role
     * /api/auth/role/{roleId}/user/{userId}
     * 
     * 
     */
    @Test
    public void testLogin() {

        serverUrl = "http://localhost:" + port + "/api"; 

        JwtResponse adminLogin = testLogin("admin", "admin");
        assertNotNull(adminLogin);

        //Headers for admin
        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminLogin.getAccessToken());
        
        //Test for unexisting user
        assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            testLogin("testuser", "testuser");
        });
        
        //Test for user creation
        //We will later use these credentials to test lack of admin role.
        SignupRequest signupRequest = new SignupRequest();
        Integer randomNumber = (int)(Math.random() * 1000);
        signupRequest.setUsername("testuser" + randomNumber);        
        signupRequest.setEmail(randomNumber +"@test.org");
        signupRequest.setPassword("testuser");


        assertDoesNotThrow(() -> {
            HttpEntity<SignupRequest> signupEntity = new HttpEntity<>(signupRequest, adminHeaders);
            restTemplate.exchange(serverUrl + "/auth/signup", HttpMethod.POST, signupEntity, String.class);
        });
       
        JwtResponse testUserLogin = testLogin("testuser" + randomNumber, "testuser");
        assertNotNull(testUserLogin);

        //Headers for test user
        HttpHeaders testuserHeaders = new HttpHeaders();
        testuserHeaders.setBearerAuth(testUserLogin.getAccessToken());

        //Test for password update
        assertDoesNotThrow(() -> {
            testuserHeaders.add("password", "newpassword");
            HttpEntity<ServerMessageResponse> updatePass = new HttpEntity<>(testuserHeaders);
            restTemplate.exchange(serverUrl + "/auth/password/update", HttpMethod.PUT, updatePass, String.class);
        });
        
        //Test for unauthorized access of roles, logged user not admin
        assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            HttpEntity<String> request = new HttpEntity<>(testuserHeaders);
            restTemplate.exchange(serverUrl + "/auth/roles", HttpMethod.GET, request, String.class);
        });

        //Test for unauthorized access of roles, not logged user
        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(serverUrl + "/auth/roles", String.class);
        });

        // Test for authorized access of roles
        assertDoesNotThrow(() -> {
            HttpEntity<String> request = new HttpEntity<>(adminHeaders);
            ResponseEntity<String> rolesResponseWithToken = restTemplate.exchange(serverUrl + "/auth/roles", HttpMethod.GET, request, String.class);
            assertNotNull(rolesResponseWithToken.getBody());
        });

        //Test for adding roles
        RoleCreationRequest testNewRole = new RoleCreationRequest();
        testNewRole.setRoleName("TST" + randomNumber);
        
        //Test for unauthorized creation of role, logged user not admin
        assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            
            HttpEntity<RoleCreationRequest> request = new HttpEntity<>(testNewRole, testuserHeaders);
            ResponseEntity<ServerMessageResponse> roleCreationResponse =
                restTemplate.postForEntity(serverUrl + "/auth/role", request, ServerMessageResponse.class);
            assertNotNull(roleCreationResponse);
        });

        //Test for unauthorized creation of role, not logged user
        assertThrows(HttpClientErrorException.class, () -> {
            HttpEntity<String> request = new HttpEntity<>(testuserHeaders);
            ResponseEntity<ServerMessageResponse> roleCreationResponse =
                restTemplate.postForEntity(serverUrl + "/auth/role", request, ServerMessageResponse.class);
            assertNotNull(roleCreationResponse);
        });

        // Test for authorized creation of role
        assertDoesNotThrow(() -> {
            HttpEntity<RoleCreationRequest> request = new HttpEntity<>(testNewRole, adminHeaders);
            ResponseEntity<RoleCreationResponse> roleCreationResponse = 
                restTemplate.postForEntity(serverUrl + "/auth/role", request, RoleCreationResponse.class);
            assertNotNull(roleCreationResponse);
        });


        //Test of new password and role assignement
        assertDoesNotThrow(() -> {
            
            JwtResponse testUserLoginNewPass =
                testLogin("testuser" + randomNumber, "newpassword");
            List<String> testUserRoles = testUserLoginNewPass.getRoles();

            HttpEntity<String> adminRequest = new HttpEntity<>(adminHeaders);
            ResponseEntity<Role[]> systemRoles
                = restTemplate.exchange(serverUrl + "/auth/roles", HttpMethod.GET, adminRequest, Role[].class);
            
            assertTrue(systemRoles.getBody().length > testUserRoles.size());

            List<Role> systemRoleList = Arrays.asList(systemRoles.getBody());

            List<Role> rolesNotAssignedToTestUser = systemRoleList.stream()
                                                .filter(role -> !testUserRoles.contains(role.getName()))
                                                .collect(Collectors.toList());

            assertNotNull(rolesNotAssignedToTestUser);
            assertTrue(rolesNotAssignedToTestUser.size() > 0);

            String roleAssignmentRequest = serverUrl + 
                                            "/auth/role/" + 
                                            rolesNotAssignedToTestUser.get(0).getId() +
                                            "/user/" + 
                                            testUserLoginNewPass.getId();
            
            ResponseEntity<ServerMessageResponse> roleCreationResponse =
                restTemplate.postForEntity(roleAssignmentRequest, 
                    adminRequest, ServerMessageResponse.class);

            assertNotNull(roleCreationResponse);
            
            assertEquals(roleCreationResponse.getBody().getCode(), HttpStatus.OK.value());
            
        }); 

        
    }

    private JwtResponse testLogin(String username, String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        //Test for successful login
        ResponseEntity<JwtResponse> responseEntity = restTemplate.postForEntity(serverUrl + "/auth/login", loginRequest, JwtResponse.class);
        return responseEntity.getBody();
    }

    

}
