package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.payload.auth.request.*;
import org.dataverse.marketplace.payload.auth.response.*;

import java.util.Map;

/**
 * Test for public user registration and settings API.
 * Tests the following endpoints:
 * - GET /api/settings/{key} (public)
 * - PUT /api/settings/{key} (admin only)
 * - POST /api/auth/signup (public when enabled, admin-only when disabled)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PublicSignupAndSettingsTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    @Order(1)
    public void testPublicSignupWhenEnabled() {
        String serverUrl = "http://localhost:" + port + "/api";

        // First, verify registration is enabled
        Map<String, String> registrationStatus = restTemplate.getForObject(
            serverUrl + "/settings/registration_enabled",
            Map.class
        );
        assertEquals("true", registrationStatus.get("value"));

        // Test public signup without authentication
        SignupRequest signupRequest = new SignupRequest();
        Integer randomNumber = (int)(Math.random() * 10000);
        signupRequest.setUsername("publictestuser" + randomNumber);
        signupRequest.setEmail("publictest" + randomNumber + "@test.org");
        signupRequest.setPassword("testpassword");

        ResponseEntity<ServerMessageResponse> signupResponse = restTemplate.postForEntity(
            serverUrl + "/auth/signup",
            signupRequest,
            ServerMessageResponse.class
        );

        assertEquals(HttpStatus.OK, signupResponse.getStatusCode());
        assertNotNull(signupResponse.getBody());
        assertEquals(200, signupResponse.getBody().getCode());

        // Verify user can login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("publictestuser" + randomNumber);
        loginRequest.setPassword("testpassword");

        ResponseEntity<JwtResponse> loginResponse = restTemplate.postForEntity(
            serverUrl + "/auth/login",
            loginRequest,
            JwtResponse.class
        );

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertEquals("publictestuser" + randomNumber, loginResponse.getBody().getUsername());

        // Verify user has NO roles
        assertTrue(loginResponse.getBody().getRoles().isEmpty(),
            "New public users should have no roles");
    }

    @Test
    @Order(2)
    public void testSettingsAPIRequiresAdminForWrite() {
        String serverUrl = "http://localhost:" + port + "/api";

        // Try to update setting without authentication - should fail
        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.put(
                serverUrl + "/settings/registration_enabled?value=false",
                null
            );
        });

        // Login as admin
        JwtResponse adminLogin = LoginAndAuthTest.testLogin("admin", "admin", restTemplate, serverUrl);
        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminLogin.getAccessToken());

        // Admin should be able to update setting
        HttpEntity<?> adminRequest = new HttpEntity<>(adminHeaders);
        ResponseEntity<ServerMessageResponse> updateResponse = restTemplate.exchange(
            serverUrl + "/settings/registration_enabled?value=false",
            HttpMethod.PUT,
            adminRequest,
            ServerMessageResponse.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertTrue(updateResponse.getBody().getMessage().contains("updated"));
    }

    @Test
    @Order(3)
    public void testPublicSignupWhenDisabled() {
        String serverUrl = "http://localhost:" + port + "/api";

        // Verify registration is disabled
        Map<String, String> registrationStatus = restTemplate.getForObject(
            serverUrl + "/settings/registration_enabled",
            Map.class
        );
        assertEquals("false", registrationStatus.get("value"));

        // Try public signup - should fail with 403
        SignupRequest signupRequest = new SignupRequest();
        Integer randomNumber = (int)(Math.random() * 10000);
        signupRequest.setUsername("faileduser" + randomNumber);
        signupRequest.setEmail("failed" + randomNumber + "@test.org");
        signupRequest.setPassword("testpassword");

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity(
                serverUrl + "/auth/signup",
                signupRequest,
                ServerMessageResponse.class
            );
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
    }

    @Test
    @Order(4)
    public void testAdminSignupWhenDisabled() {
        String serverUrl = "http://localhost:" + port + "/api";

        // Login as admin
        JwtResponse adminLogin = LoginAndAuthTest.testLogin("admin", "admin", restTemplate, serverUrl);
        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminLogin.getAccessToken());

        // Admin should still be able to create users even when registration is disabled
        SignupRequest signupRequest = new SignupRequest();
        Integer randomNumber = (int)(Math.random() * 10000);
        signupRequest.setUsername("admincreated" + randomNumber);
        signupRequest.setEmail("admincreated" + randomNumber + "@test.org");
        signupRequest.setPassword("testpassword");

        HttpEntity<SignupRequest> adminRequest = new HttpEntity<>(signupRequest, adminHeaders);
        ResponseEntity<ServerMessageResponse> signupResponse = restTemplate.exchange(
            serverUrl + "/auth/signup",
            HttpMethod.POST,
            adminRequest,
            ServerMessageResponse.class
        );

        assertEquals(HttpStatus.OK, signupResponse.getStatusCode());
        assertNotNull(signupResponse.getBody());
        assertEquals(200, signupResponse.getBody().getCode());

        // Re-enable registration for other tests
        HttpEntity<?> reEnableRequest = new HttpEntity<>(adminHeaders);
        restTemplate.exchange(
            serverUrl + "/settings/registration_enabled?value=true",
            HttpMethod.PUT,
            reEnableRequest,
            ServerMessageResponse.class
        );
    }

    @Test
    @Order(5)
    public void testSettingsGetIsPublic() {
        String serverUrl = "http://localhost:" + port + "/api";

        // Anyone should be able to read settings without authentication
        Map<String, String> registrationStatus = restTemplate.getForObject(
            serverUrl + "/settings/registration_enabled",
            Map.class
        );

        assertNotNull(registrationStatus);
        assertTrue(registrationStatus.containsKey("key"));
        assertTrue(registrationStatus.containsKey("value"));
        assertEquals("registration_enabled", registrationStatus.get("key"));
    }

    @Test
    @Order(6)
    public void testSettingsNotFound() {
        String serverUrl = "http://localhost:" + port + "/api";

        // Request non-existent setting should return 404
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject(
                serverUrl + "/settings/nonexistent_setting",
                Map.class
            );
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
