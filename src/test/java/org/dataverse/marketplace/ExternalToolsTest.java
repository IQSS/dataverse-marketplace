package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.AddVersionRequest;
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

import static org.dataverse.marketplace.TestUtils.assertPresent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExternalToolsTest {

        @LocalServerPort
        private int port;

        private RestTemplate restTemplate = new RestTemplate();

        @Test
        public void TestExternalTools() throws InterruptedException {

                String serverUrl = "http://localhost:" + port + "/api/";
                String toolsUrl = serverUrl + "/tools/";
                String versionsUrl = serverUrl + "/versions/";

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

                // Test add without images
                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                AddToolRequest addToolRequest = new AddToolRequest();
                addToolRequest.setName("AskTheData");
                addToolRequest.setDescription("Ask the Data from tests");
                addToolRequest.setVersionName("1.0");
                addToolRequest.setVersionNote("This release includes a new feature ...");
                addToolRequest.setDataverseMinVersion("6.0");


                ResponseEntity<ExternalToolDTO> postToolResponse = restTemplate.postForEntity(serverUrl + "/tools",
                                new HttpEntity<>(addToolRequest, adminHeaders), ExternalToolDTO.class);
                assertEquals(postToolResponse.getStatusCode(), HttpStatus.OK);

                // test permissions (with no user)
                assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
                        restTemplate.postForEntity(serverUrl + "/tools", new HttpEntity<>(addToolRequest, userHeaders),
                                        ServerMessageResponse.class);
                });

                String newToolPostUrl = toolsUrl + assertPresent(postToolResponse).getId();

                // Get tool by ID test
                ResponseEntity<ExternalToolDTO> getToolsByIdResponse = restTemplate.getForEntity(newToolPostUrl,
                                ExternalToolDTO.class);
                assertEquals(getToolsByIdResponse.getStatusCode(), HttpStatus.OK);


                // Test images post
                String imagesUrl = newToolPostUrl + "/images";
                File image = new File("src/test/resources/dv_logo.png");
                assertEquals(image.exists(), true);                
                adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> imagePostBody = new LinkedMultiValueMap<>();
                imagePostBody.add("images", new FileSystemResource(image));

                ResponseEntity<MarketplaceItemImageDTO[]> postImagesResponse = restTemplate.postForEntity(imagesUrl,
                                new HttpEntity<>(imagePostBody, adminHeaders),
                                MarketplaceItemImageDTO[].class);
                assertEquals(postImagesResponse.getStatusCode(), HttpStatus.OK);


                // Get Images test
                ResponseEntity<MarketplaceItemImageDTO[]> getImagesResponse = restTemplate.getForEntity(imagesUrl,
                                MarketplaceItemImageDTO[].class);
                assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getImagesResponse).length, 1);

                // Test get stored resource
                ResponseEntity<byte[]> getStoredResourceResponse = restTemplate.getForEntity(
                                serverUrl + "/stored-resource/"
                                                + assertPresent(getImagesResponse)[0].getStoredResourceId(),
                                byte[].class);
                assertEquals(getStoredResourceResponse.getStatusCode(), HttpStatus.OK);

                // Test delete image
                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                ResponseEntity<ServerMessageResponse> deleteImageResponse = restTemplate.exchange(
                                newToolPostUrl + "/images/" + assertPresent(getImagesResponse)[0].getStoredResourceId(),
                                HttpMethod.DELETE,
                                new HttpEntity<>(adminHeaders),
                                ServerMessageResponse.class);
                assertEquals(deleteImageResponse.getStatusCode(), HttpStatus.OK);

                getImagesResponse = restTemplate.getForEntity(
                                imagesUrl,
                                MarketplaceItemImageDTO[].class);
                assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getImagesResponse).length, 0);

                // Test get versions
                ResponseEntity<ExternalToolVersionDTO[]> getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getVersionsResponse).length, 1);

                // Test get version by ID
                System.out.println("GETTING VERSION+ " + assertPresent(getVersionsResponse)[0].getId());

                ResponseEntity<ExternalToolVersionDTO> getVersionByIdResponse = restTemplate.getForEntity(versionsUrl
                                + assertPresent(getVersionsResponse)[0].getId(),
                                ExternalToolVersionDTO.class);
                assertEquals(getVersionByIdResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getVersionsResponse)[0].toString(),
                                assertPresent(getVersionByIdResponse).toString());

                // Test post version
                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                AddVersionRequest addVersionRequest = new AddVersionRequest();
                addVersionRequest.setVersionName("1.1");     
                addVersionRequest.setVersionNote("This release updates ...");
                addVersionRequest.setDataverseMinVersion("6.0");
           

                ResponseEntity<ExternalToolVersionDTO> postVersionResponse = restTemplate.postForEntity(
                                newToolPostUrl + "/versions",
                                new HttpEntity<>(addVersionRequest, adminHeaders),
                                ExternalToolVersionDTO.class);
                assertEquals(postVersionResponse.getStatusCode(), HttpStatus.OK);

                getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getVersionsResponse).length, 2);

                // Test put version
                final String UPDATED = "UPDATED";
                Map<String, String> putVersionBody = new HashMap<String, String>();
                putVersionBody.put("version", UPDATED);
                putVersionBody.put("releaseNote", UPDATED);
                putVersionBody.put("dvMinVersion", UPDATED);

                adminHeaders.setContentType(MediaType.APPLICATION_JSON);

                ResponseEntity<ExternalToolVersionDTO> putVersionResponse = restTemplate.exchange(
                                versionsUrl + assertPresent(postVersionResponse).getId(),
                                HttpMethod.PUT,
                                new HttpEntity<>(putVersionBody, adminHeaders),
                                ExternalToolVersionDTO.class);

                assertEquals(putVersionResponse.getStatusCode(), HttpStatus.OK);

                getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getVersionsResponse).length, 2);
                assertEquals(assertPresent(getVersionsResponse)[1].getVersionName(), UPDATED);
                assertEquals(assertPresent(getVersionsResponse)[1].getVersionNote(), UPDATED);
                assertEquals(assertPresent(getVersionsResponse)[1].getDataverseMinVersion(), UPDATED);

                // Test get manifest
                ResponseEntity<ExternalToolManifestDTO[]> getManifestResponse = restTemplate.getForEntity(
                                versionsUrl + assertPresent(getVersionsResponse)[0].getId() + "/manifests",
                                ExternalToolManifestDTO[].class);

                assertEquals(getManifestResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getManifestResponse).length, 0);

                // Test post manifest
                File manifest = new File("src/test/resources/askthedata.json");
                assertEquals(manifest.exists(), true);
                String jsonData = "";
                try {
                        jsonData = Files.readString(manifest.toPath(), StandardCharsets.UTF_8);
                        assertFalse(jsonData.isBlank(), "jsonData should not be empty");

                } catch (IOException e) {
                        fail("Failed to read manifest file", e);
                }

                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                putVersionResponse = restTemplate.exchange(
                                versionsUrl + assertPresent(getVersionsResponse)[0].getId() + "/manifest",
                                HttpMethod.PUT,
                                new HttpEntity<>(jsonData, adminHeaders), ExternalToolVersionDTO.class);

                assertEquals(putVersionResponse.getStatusCode(), HttpStatus.OK);

                getManifestResponse = restTemplate.getForEntity(
                                versionsUrl + assertPresent(getVersionsResponse)[0].getId() + "/manifests",
                                ExternalToolManifestDTO[].class);

                assertEquals(getManifestResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getManifestResponse).length, 1);

                // Test delete version
                adminHeaders.setContentType(MediaType.APPLICATION_JSON);
                ResponseEntity<ServerMessageResponse> deleteVersionResponse = restTemplate.exchange(
                                versionsUrl + assertPresent(getVersionsResponse)[1].getId(),
                                org.springframework.http.HttpMethod.DELETE, new HttpEntity<>(adminHeaders),
                                ServerMessageResponse.class);
                assertEquals(deleteVersionResponse.getStatusCode(), HttpStatus.OK);

                getVersionsResponse = restTemplate.getForEntity(
                                newToolPostUrl + "/versions",
                                ExternalToolVersionDTO[].class);
                assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
                assertEquals(assertPresent(getVersionsResponse).length, 1);

        }

}
