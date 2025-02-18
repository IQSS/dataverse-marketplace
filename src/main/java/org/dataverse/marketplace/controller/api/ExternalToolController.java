package org.dataverse.marketplace.controller.api;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.annotations.ExternalToolsAPIDocs;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.service.ExternalToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/tools")
public class ExternalToolController {

    @Autowired
    private ExternalToolService externalToolService;

    /**
     * Method to retrieve all external tools     
     */
    @GetMapping()
    @ExternalToolsAPIDocs.ExternalToolsList
    public ResponseEntity<?> getAllTools() {

        List<ExternalTool> tools = externalToolService.getAllTools();
        ArrayList<ExternalToolDTO> toolDTOs = new ArrayList<>();
        for (ExternalTool tool : tools) {
            toolDTOs.add(new ExternalToolDTO(tool));
        }

        return ResponseEntity.ok(toolDTOs);
    }

    /**
     * Method to retrieve all external tools     
     */
    @GetMapping("/{toolId}")
    @ExternalToolsAPIDocs.GetExternalToolById
    public ResponseEntity<?> getToolById(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);
        return ResponseEntity.ok(new ExternalToolDTO(tool));
    }

    /**
     * Method to add a new external tool
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddExternalToolsRequest   
    public ResponseEntity<?> addNewTool(@Valid AddToolRequest addToolRequest) throws IOException{
        return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
    }
    
    @Hidden
    @GetMapping("/{toolId}/versions/{versionId}/manifests")
    public ResponseEntity<?> getVersionManifestsById(@PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId) {

        List<ExternalToolManifest> manifests = externalToolService.getToolManifests(toolId, versionId);
        List<Long> manifestStoredResourceId = new ArrayList<>();
        for (ExternalToolManifest manifest : manifests) {
            manifestStoredResourceId.add(manifest.getManifestStoredResourceId());
        }
        return ResponseEntity.ok(manifestStoredResourceId);
    }

    
    @GetMapping("/{toolId}/images")
    public ResponseEntity<?> getToolImages(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        List<Long> imagesResourceId = new ArrayList<>();
        for (MarketplaceItemImage image : tool.getImages()) {
            imagesResourceId.add(image.getImageStoredResourceId());
        }
        
        return ResponseEntity.ok(imagesResourceId);
    }

    
    @PostMapping(path = "/{toolId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addToolImages(@PathVariable("toolId") Integer toolId, 
            @RequestBody List<MultipartFile> images) throws IOException {

        ExternalTool tool = new ExternalTool();
        tool.setId(toolId);

        List<MarketplaceItemImage> mktImages = externalToolService.addItemImages(tool, images);

        ArrayList<Long> imagesResourceId = new ArrayList<>();
        
        for(MarketplaceItemImage img : mktImages){
            imagesResourceId.add(img.getImageStoredResourceId());
        }            

        return ResponseEntity.ok(imagesResourceId);
    }

    @DeleteMapping("/{toolId}/images/{imageId}")
    public ResponseEntity<?> deleteToolImage(
            @PathVariable("toolId") Integer toolId, 
            @PathVariable("imageId") Long imageId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if(tool == null){
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }

        
    }






}
