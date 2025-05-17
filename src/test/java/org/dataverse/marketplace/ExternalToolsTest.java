package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.payload.ExternalToolManifestDTO;
import org.dataverse.marketplace.payload.ExternalToolVersionDTO;
import org.dataverse.marketplace.payload.MarketplaceItemImageDTO;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.auth.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExternalToolsTest {

        @LocalServerPort
        private int port;

        private RestTemplate restTemplate = new RestTemplate();

        @Test
        public void TestExternalTools() throws InterruptedException {   

                String serverUrl = "http://localhost:" + port + "/api/";
                String toolsUrl = serverUrl + "/tools/";

                JwtResponse adminLogin = LoginAndAuthTest.testLogin("admin", "admin", restTemplate, serverUrl);
                assertNotNull(adminLogin);

                // Headers for admin
                HttpHeaders adminHeaders = new HttpHeaders();
                adminHeaders.setBearerAuth(adminLogin.getAccessToken());

                // Unauthorized user
                HttpHeaders userHeaders = new HttpHeaders();

                // Get tools test
                ResponseEntity<ExternalToolDTO[]> getToolsResponse = restTemplate.getForEntity(serverUrl + "/tools",
                                ExternalToolDTO[].class);
                assertEquals(getToolsResponse.getStatusCode(), HttpStatus.OK);

                // Post tools test
                File jsonData = new File("src/test/resources/askthedata.json");
                assertEquals(jsonData.exists(), true);

                File image = new File("src/test/resources/dv_logo.png");
                assertEquals(image.exists(), true);
                String contentType = adminHeaders.getContentType() != null ? adminHeaders.getContentType().toString() : "no val";
                adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> postNewToolBody = new LinkedMultiValueMap<>();
                postNewToolBody.add("name", "AskTheData");
                postNewToolBody.add("description",contentType);
                postNewToolBody.add("dvMinVersion", "6.0");
                postNewToolBody.add("releaseNote", "This release includes a new feature ...");
                postNewToolBody.add("version", "1.0");
                postNewToolBody.add("jsonData", new FileSystemResource(jsonData));
                postNewToolBody.add("itemImages", new FileSystemResource(image));

                // admin does not have editor role, can not add a new tool
                assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
                        restTemplate.postForEntity(serverUrl + "/tools",
                                        new HttpEntity<>(postNewToolBody, adminHeaders),
                                        ServerMessageResponse.class);
                });

                // add the role and try again
                TestUtils.assignRole(serverUrl, adminLogin, adminLogin.getId(), "EDITOR");

                ResponseEntity<ExternalToolDTO> postToolResponse = restTemplate.postForEntity(serverUrl + "/tools",
                                new HttpEntity<>(postNewToolBody, adminHeaders), ExternalToolDTO.class);
                assertEquals(postToolResponse.getStatusCode(), HttpStatus.OK);

                // and remove the role
                TestUtils.removeRole(serverUrl, adminLogin, adminLogin.getId(), "EDITOR");


                assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
                        restTemplate.postForEntity(serverUrl + "/tools", new HttpEntity<>(postNewToolBody, userHeaders),
                                        ServerMessageResponse.class);
                });

                assertNotNull(postToolResponse.getBody());
                String newToolPostUrl = toolsUrl + postToolResponse.getBody().getId();

                // Get tool by ID test
                ResponseEntity<ExternalToolDTO> getToolsByIdResponse = restTemplate.getForEntity(newToolPostUrl,
                                ExternalToolDTO.class);
                assertEquals(getToolsByIdResponse.getStatusCode(), HttpStatus.OK);

                // Get Images test
                String imagesUrl = newToolPostUrl + "/images";
                ResponseEntity<MarketplaceItemImageDTO[]> getImagesResponse = restTemplate.getForEntity(imagesUrl,
                                MarketplaceItemImageDTO[].class);
                assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getImagesResponse.getBody().length, 1);

                // Test get stored resource
                ResponseEntity<byte[]> getStoredResourceResponse = restTemplate.getForEntity(
                                serverUrl + "/stored-resource/" + getImagesResponse.getBody()[0].getStoredResourceId(),
                                byte[].class);
                assertEquals(getStoredResourceResponse.getStatusCode(), HttpStatus.OK);

                // Test images post
                adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> imagePostBody = new LinkedMultiValueMap<>();
                imagePostBody.add("images", new FileSystemResource(image));

                ResponseEntity<MarketplaceItemImageDTO[]> postImagesResponse = restTemplate.postForEntity(imagesUrl,
                                new HttpEntity<>(imagePostBody, adminHeaders),
                                MarketplaceItemImageDTO[].class);
                assertEquals(postImagesResponse.getStatusCode(), HttpStatus.OK);

                getImagesResponse = restTemplate.getForEntity(imagesUrl,
                                MarketplaceItemImageDTO[].class);
                assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getImagesResponse.getBody().length, 2);

                // Test delete image
                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                ResponseEntity<ServerMessageResponse> deleteImageResponse = restTemplate.exchange(
                                newToolPostUrl + "/images/" + getImagesResponse.getBody()[0].getStoredResourceId(),
                                HttpMethod.DELETE,
                                new HttpEntity<>(adminHeaders),
                                ServerMessageResponse.class);
                assertEquals(deleteImageResponse.getStatusCode(), HttpStatus.OK);

                getImagesResponse = restTemplate.getForEntity(
                                imagesUrl,
                                MarketplaceItemImageDTO[].class);
                assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getImagesResponse.getBody().length, 1);

                // Test get version
                ResponseEntity<ExternalToolVersionDTO[]> getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getVersionsResponse.getBody().length, 1);

                // Test get version by ID
                ResponseEntity<ExternalToolVersionDTO> getVersionByIdResponse = restTemplate.getForEntity(toolsUrl
                                + postToolResponse.getBody().getId() + "/versions/"
                                + getVersionsResponse.getBody()[0].getId(),
                                ExternalToolVersionDTO.class);
                assertEquals(getVersionByIdResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getVersionsResponse.getBody()[0].toString(), getVersionByIdResponse.getBody().toString());

                // Test post version
                adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> postVersionBody = new LinkedMultiValueMap<>();
                postVersionBody.add("version", "1.1");
                postVersionBody.add("jsonData", new FileSystemResource(jsonData));
                postVersionBody.add("releaseNote",
                                "This release includes ...");
                postVersionBody.add("dvMinVersion", "6.0");

                ResponseEntity<ExternalToolVersionDTO> postVersionResponse = restTemplate.postForEntity(
                                newToolPostUrl + "/versions",
                                new HttpEntity<>(postVersionBody, adminHeaders),
                                ExternalToolVersionDTO.class);
                assertEquals(postVersionResponse.getStatusCode(), HttpStatus.OK);

                getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getVersionsResponse.getBody().length, 2);

                // Test put version
                final String UPDATED = "UPDATED";
                Map<String, String> putVersionBody = new HashMap<String, String>();
                putVersionBody.put("version", UPDATED);
                putVersionBody.put("releaseNote", UPDATED);
                putVersionBody.put("dvMinVersion", UPDATED);

                adminHeaders.setContentType(MediaType.APPLICATION_JSON);

                ResponseEntity<ExternalToolVersionDTO> putVersionResponse = restTemplate.exchange(
                                newToolPostUrl +
                                                "/versions/" + postVersionResponse.getBody().getId(),
                                HttpMethod.PUT,
                                new HttpEntity<>(putVersionBody, adminHeaders),
                                ExternalToolVersionDTO.class);

                assertEquals(putVersionResponse.getStatusCode(), HttpStatus.OK);

                getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getVersionsResponse.getBody().length, 2);
                assertEquals(getVersionsResponse.getBody()[1].getReleaseNote(), UPDATED);
                assertEquals(getVersionsResponse.getBody()[1].getVersion(), UPDATED);
                assertEquals(getVersionsResponse.getBody()[1].getDataverseMinVersion(), UPDATED);

                // Test get manifest
                ResponseEntity<ExternalToolManifestDTO[]> getManifestResponse = restTemplate.getForEntity(
                                newToolPostUrl
                                                + "/versions/" + getVersionsResponse.getBody()[0].getId()
                                                + "/manifests",
                                ExternalToolManifestDTO[].class);

                assertEquals(getManifestResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getManifestResponse.getBody().length, 1);

                // Test post manifest
                File manifest = new File("src/test/resources/askthedata.json");
                assertEquals(manifest.exists(), true);

                adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> postManifestBody = new LinkedMultiValueMap<>();
                postManifestBody.add("jsonData", new FileSystemResource(manifest));

                ResponseEntity<ExternalToolManifestDTO[]> postManifestResponse = restTemplate.postForEntity(
                                newToolPostUrl + "/versions/"
                                                + getVersionsResponse.getBody()[0].getId() + "/manifests",
                                new HttpEntity<>(postManifestBody, adminHeaders), ExternalToolManifestDTO[].class);
                assertEquals(postManifestResponse.getStatusCode(), HttpStatus.OK);

                getManifestResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions/"
                                                + getVersionsResponse.getBody()[0].getId() + "/manifests",
                                ExternalToolManifestDTO[].class);

                assertEquals(getManifestResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getManifestResponse.getBody().length, 2);



                getManifestResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions/"
                                                + getVersionsResponse.getBody()[0].getId() + "/manifests",
                                ExternalToolManifestDTO[].class);

                assertEquals(getManifestResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getManifestResponse.getBody().length, 1);

                // Test delete version
                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                ResponseEntity<ServerMessageResponse> deleteVersionResponse = restTemplate.exchange(
                                newToolPostUrl + "/versions/"
                                                + getVersionsResponse.getBody()[1].getId(),
                                org.springframework.http.HttpMethod.DELETE, new HttpEntity<>(adminHeaders),
                                ServerMessageResponse.class);
                assertEquals(deleteVersionResponse.getStatusCode(), HttpStatus.OK);

                getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(getVersionsResponse.getBody().length, 1);

        }     

}
