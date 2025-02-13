package org.dataverse.marketplace.controller.api;

import java.nio.file.NoSuchFileException;

import org.dataverse.marketplace.model.StoredResource;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.service.ResourceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/api/stored-resource")
public class StoredResourceController {

    @Autowired
    private ResourceStorageService resourceStorageService;

    @GetMapping()
    public ResponseEntity<?> getFile(@RequestParam Long storedResourceId) throws Exception {
        try {
            StoredResource storedResource = resourceStorageService.getStoredResourceById(storedResourceId);
            Resource file = resourceStorageService.getResourceBytes(storedResource);
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(storedResource.getMimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
        } catch (NoSuchFileException e) {

            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested resource with ID %d was not found in the storage service.", storedResourceId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
    }


}
