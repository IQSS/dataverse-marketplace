package org.dataverse.marketplace;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.payload.auth.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockMultipartFile;
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

        AddToolRequest addToolRequest = new AddToolRequest();
        addToolRequest.setName("Test Tool");
        addToolRequest.setDescription("Test Description");
        addToolRequest.setDvMinVersion(serverUrl);
        addToolRequest.setReleaseNote("Test Release Note");
        addToolRequest.setVersion("1.0");

        assertDoesNotThrow(() -> {
            File file = new File("src/test/resources/askthedata.json");
            assertEquals(file.exists(), true);
            byte[] content = Files.readAllBytes(file.toPath());

            ArrayList<MultipartFile> jsonData = new ArrayList<>();
            MockMultipartFile mockMultipartFile = new MockMultipartFile("jsonData", "test.json", "application/json", content);
            jsonData.add(mockMultipartFile);

            //HttpEntity<AddToolRequest> addToolRequestEntity = new HttpEntity<>(new AddToolRequest());
            restTemplate.postForEntity(serverUrl + "/tools", addToolRequest, ExternalToolDTO.class);

        });
        

       

       

        

        
    }

}
