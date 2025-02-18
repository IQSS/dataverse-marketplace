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

    @Hidden
    @GetMapping("/{toolId}/images")
    public ResponseEntity<?> getToolImages(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        List<Long> imagesResourceId = new ArrayList<>();
        for (MarketplaceItemImage image : tool.getImages()) {
            imagesResourceId.add(image.getManifestStoredResourceId());
        }
        
        return ResponseEntity.ok(imagesResourceId);
    }



}
