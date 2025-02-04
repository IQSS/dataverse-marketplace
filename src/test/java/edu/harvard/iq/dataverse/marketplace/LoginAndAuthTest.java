package edu.harvard.iq.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.harvard.iq.dataverse.marketplace.payload.ServerMessageResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.LoginRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.RoleCreationRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.SignupRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.response.JwtResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.response.RoleCreationResponse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginAndAuthTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();
    private String serverUrl;

    /**
     * Test for login, authentication and user management.
     * 
     */
    @Test
    public void testLogin() {

        serverUrl = "http://localhost:" + port + "/api"; 
        //RestTemplate restTemplate = new RestTemplateBuilder().rootUri(serverUrl).build();

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
        //Test of new password
        assertDoesNotThrow(() -> {
            testLogin("testuser" + randomNumber, "newpassword");
        }); 

        //Test of roles access
        
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
        
        //Test for unauthorized access of roles, logged user not admin
        assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
            
            HttpEntity<RoleCreationRequest> request = new HttpEntity<>(testNewRole, testuserHeaders);
            ResponseEntity<ServerMessageResponse> roleCreationResponse =
                restTemplate.postForEntity(serverUrl + "/auth/role", request, ServerMessageResponse.class);
            assertNotNull(roleCreationResponse);
        });

        //Test for unauthorized access of roles, not logged user
        assertThrows(HttpClientErrorException.class, () -> {
            HttpEntity<String> request = new HttpEntity<>(testuserHeaders);
            ResponseEntity<ServerMessageResponse> roleCreationResponse =
                restTemplate.postForEntity(serverUrl + "/auth/role", request, ServerMessageResponse.class);
            assertNotNull(roleCreationResponse);
        });

        // Test for authorized access of roles
        assertDoesNotThrow(() -> {
            HttpEntity<RoleCreationRequest> request = new HttpEntity<>(testNewRole, adminHeaders);
            ResponseEntity<RoleCreationResponse> roleCreationResponse = 
                restTemplate.postForEntity(serverUrl + "/auth/role", request, RoleCreationResponse.class);
            assertNotNull(roleCreationResponse);
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
