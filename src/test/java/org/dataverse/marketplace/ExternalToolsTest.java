package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.apache.catalina.connector.Response;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.payload.auth.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

       

        assertDoesNotThrow(() -> {
            File file = new File("src/test/resources/askthedata.json");
            assertEquals(file.exists(), true);
           
            

            adminHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("name", "AskTheData");
            body.add("description", "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.");
            body.add("dvMinVersion", "6.0");
            body.add("releaseNote", "This release includes a new feature that allows you to ask questions to an LLM.");
            body.add("version", "1.0");
            body.add("jsonData", new FileSystemResource(file));
            
            //HttpEntity<AddToolRequest> addToolRequestEntity = new HttpEntity<>(new AddToolRequest());
            //restTemplate.postForEntity(serverUrl + "/tools", addToolRequest, ExternalToolDTO.class);
            ResponseEntity<ExternalToolDTO> response = restTemplate.postForEntity(serverUrl + "/tools", new HttpEntity<>(body, adminHeaders), ExternalToolDTO.class);
            System.out.println(response.getBody());

        });
        

       

       

        

        
    }

}
