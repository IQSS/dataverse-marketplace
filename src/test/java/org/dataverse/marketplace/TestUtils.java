package org.dataverse.marketplace;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.dataverse.marketplace.model.Role;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.auth.response.JwtResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestUtils {

        private static RestTemplate restTemplate = new RestTemplate();

        public static void assignRole (String serverUrl, JwtResponse adminLogin, Long userId, String roleName) {

                HttpHeaders roleAssignmentHeaders = new HttpHeaders();
                roleAssignmentHeaders.setBearerAuth(adminLogin.getAccessToken()); 
                HttpEntity<String> adminRequest = new HttpEntity<>(roleAssignmentHeaders);

                ResponseEntity<Role[]> systemRoles
                        = restTemplate.exchange(serverUrl + "/auth/roles", HttpMethod.GET, adminRequest, Role[].class);
            
                List<Role> systemRoleList = Arrays.asList(systemRoles.getBody());
                List<Role> roleToAssign = systemRoleList.stream()
                                                .filter(role -> roleName.equals(role.getName()))
                                                .collect(Collectors.toList());
                
                // TODO CHECK IF ROLE ALREADY ASSIGNED
                String roleAssignmentRequest = serverUrl + 
                                            "/auth/roles/" +  roleToAssign.get(0).getId() +
                                            "/user/" + userId;  

                restTemplate.postForEntity(roleAssignmentRequest, adminRequest, ServerMessageResponse.class);
        }

        public static void removeRole (String serverUrl, JwtResponse adminLogin, Long userId, String roleName) {

                HttpHeaders roleAssignmentHeaders = new HttpHeaders();
                roleAssignmentHeaders.setBearerAuth(adminLogin.getAccessToken()); 
                HttpEntity<String> adminRequest = new HttpEntity<>(roleAssignmentHeaders);                

                ResponseEntity<Role[]> systemRoles
                        = restTemplate.exchange(serverUrl + "/auth/roles", HttpMethod.GET, adminRequest, Role[].class);
            
                List<Role> systemRoleList = Arrays.asList(systemRoles.getBody());
                List<Role> roleToRemove = systemRoleList.stream()
                                                .filter(role -> roleName.equals(role.getName()))
                                                .collect(Collectors.toList());

                // TODO CHECK IF ROLE IS ASSIGNED                                
                String roleAssignmentRequest = serverUrl + 
                                            "/auth/roles/" + roleToRemove.get(0).getId() +
                                            "/user/" + userId;  

                restTemplate.exchange(roleAssignmentRequest, 
                        org.springframework.http.HttpMethod.DELETE, adminRequest, ServerMessageResponse.class);
        }   

}
