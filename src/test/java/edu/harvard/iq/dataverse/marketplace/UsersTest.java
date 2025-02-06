package edu.harvard.iq.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import edu.harvard.iq.dataverse.marketplace.payload.auth.UserDTO;
import edu.harvard.iq.dataverse.marketplace.payload.auth.request.LoginRequest;
import edu.harvard.iq.dataverse.marketplace.payload.auth.response.JwtResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();
    private String serverUrl;

    @Test
    public void testUsers() {
    
        serverUrl = "http://localhost:" + port + "/api"; 
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");

        //Test for successful login
        ResponseEntity<JwtResponse> responseEntity = restTemplate.postForEntity(serverUrl + "/auth/login", loginRequest, JwtResponse.class);
        JwtResponse adminLogin = responseEntity.getBody();
        assertNotNull(adminLogin);

        //Headers for admin
        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminLogin.getAccessToken());
        
        assertDoesNotThrow(() -> {
            HttpEntity<String> request = new HttpEntity<>(adminHeaders);
            UserDTO[] users = restTemplate.exchange(serverUrl + "/user", HttpMethod.GET, request, UserDTO[].class).getBody();
            assertNotNull(users);
            assert(users.length > 0);

            UserDTO user = restTemplate.exchange(serverUrl + "/user/" + users[0].getId(), HttpMethod.GET, request, UserDTO.class).getBody();
            assertNotNull(user);
        });
    }

}
