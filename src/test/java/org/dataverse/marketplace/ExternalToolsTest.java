package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.dataverse.marketplace.payload.ExternalToolDTO;
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
    private String serverUrl;

    @Test
    public void TestExternalTools() throws InterruptedException {

        serverUrl = "http://localhost:" + port + "/api";

        JwtResponse adminLogin = LoginAndAuthTest.testLogin("admin", "admin", restTemplate, serverUrl);
        assertNotNull(adminLogin);

        //Headers for admin
        HttpHeaders adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminLogin.getAccessToken());
        // Unauthorized user
        HttpHeaders userHeaders = new HttpHeaders();
       
        // Get tools test
        ResponseEntity<ExternalToolDTO[]> getToolsResponse = restTemplate.getForEntity(serverUrl + "/tools", ExternalToolDTO[].class);
        assertEquals(getToolsResponse.getStatusCode(), HttpStatus.OK);


        // Post tools test
        File jsonData = new File("src/test/resources/askthedata.json");
        assertEquals(jsonData.exists(), true);

        File image = new File("src/test/resources/dv_logo.png");
        assertEquals(image.exists(), true);

        adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "AskTheData");
        body.add("description", "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.");
        body.add("dvMinVersion", "6.0");
        body.add("releaseNote", "This release includes a new feature that allows you to ask questions to an LLM.");
        body.add("version", "1.0");
        body.add("jsonData", new FileSystemResource(jsonData));
        body.add("itemImages", new FileSystemResource(image));
        
        ResponseEntity<ExternalToolDTO> postToolResponse = restTemplate.postForEntity(serverUrl + "/tools", new HttpEntity<>(body, adminHeaders), ExternalToolDTO.class);
        assertEquals(postToolResponse.getStatusCode(), HttpStatus.OK);

        assertThrows(HttpClientErrorException.Unauthorized.class, () ->{
            restTemplate.postForEntity(serverUrl + "/tools", new HttpEntity<>(body, userHeaders), ServerMessageResponse.class);                
        });

        // Get tool by ID test
        ResponseEntity<ExternalToolDTO> getToolsByIdResponse = restTemplate.getForEntity(serverUrl + "/tools/" + postToolResponse.getBody().getId(), ExternalToolDTO.class);
        assertEquals(getToolsByIdResponse.getStatusCode(), HttpStatus.OK);
            
        // Get Images test
        ResponseEntity<MarketplaceItemImageDTO[]> getImagesResponse = restTemplate.getForEntity(serverUrl + "/tools/" + postToolResponse.getBody().getId() + "/images", MarketplaceItemImageDTO[].class);
        assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(getImagesResponse.getBody().length, 1);

        // Test get stored resource
        ResponseEntity<byte[]> getStoredResourceResponse = restTemplate.getForEntity(serverUrl + "/stored-resource/" + getImagesResponse.getBody()[0].getStoredResourceId(), byte[].class);
        assertEquals(getStoredResourceResponse.getStatusCode(), HttpStatus.OK);

        //Test images post

        adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> imagePostBody = new LinkedMultiValueMap<>();
        imagePostBody.add("images", new FileSystemResource(image));

        ResponseEntity<ServerMessageResponse> postImagesResponse = restTemplate.postForEntity(serverUrl + "/tools/" + postToolResponse.getBody().getId() + "/images", new HttpEntity<>(imagePostBody, adminHeaders), ServerMessageResponse.class);
        assertEquals(postImagesResponse.getStatusCode(), HttpStatus.OK);

        
        getImagesResponse = restTemplate.getForEntity(serverUrl + "/tools/" + postToolResponse.getBody().getId() + "/images", MarketplaceItemImageDTO[].class);
        assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(getImagesResponse.getBody().length, 2);

        // Test delete image
        adminHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ServerMessageResponse> deleteImageResponse = restTemplate.exchange(serverUrl + "/tools/" + postToolResponse.getBody().getId() + "/images/" + getImagesResponse.getBody()[0].getStoredResourceId(), org.springframework.http.HttpMethod.DELETE, new HttpEntity<>(adminHeaders), ServerMessageResponse.class);
        assertEquals(deleteImageResponse.getStatusCode(), HttpStatus.OK);

        getImagesResponse = restTemplate.getForEntity(serverUrl + "/tools/" + postToolResponse.getBody().getId() + "/images", MarketplaceItemImageDTO[].class);
        assertEquals(getImagesResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(getImagesResponse.getBody().length, 1);

        // Test get version

        ResponseEntity<ExternalToolVersionDTO[]> getVersionsResponse = restTemplate.getForEntity(serverUrl + "/tools/" + postToolResponse.getBody().getId() + "/versions", ExternalToolVersionDTO[].class);
        assertEquals(getVersionsResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(getVersionsResponse.getBody().length, 1);
        
    }

}
