package org.dataverse.marketplace.controller.api;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
        
    })
    public ResponseEntity<?> addNewTool(@Valid @RequestBody AddToolRequest addToolRequest) throws IOException{
        
        return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
    }

    /**
     * Method to retrieve all external tools     
     */
    @GetMapping("/{toolId}/versions")
    //DOCS
    public ResponseEntity<?> getVersionsToolByToolId(@PathVariable("toolId") Integer toolId) {

        
        ExternalTool tool = externalToolService.getToolById(toolId);
        if(tool == null){
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
        List<ExternalToolVersionDTO> versions = new ArrayList<>();
        for (ExternalToolVersion version : tool.getExternalToolVersions()) {
            versions.add(new ExternalToolVersionDTO(version));
        }

        return ResponseEntity.ok(versions);
    }

    @GetMapping("/{toolId}/versions/{versionId}")
    //DOCS
    public ResponseEntity<?> getVersionById(@PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId) {
        
        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);
        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    @PutMapping("/{toolId}/versions/{versionId}")
    @ExternalToolsAPIDocs.UpdateVersionByIdDocs
    public ResponseEntity<?> updateVersionById(@PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId,
            @Valid @RequestBody ToolVersionRequest updateToolVersionRequest) {
        
        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);

        VersionMetadata metadata = version.getVersionMetadata();
        metadata.setDataverseMinVersion(updateToolVersionRequest.getDvMinVersion());
        metadata.setReleaseNote(updateToolVersionRequest.getReleaseNote());
        metadata.setVersion(updateToolVersionRequest.getVersion());

        externalToolService.updateToolVersion(version);

        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    
    @GetMapping("/{toolId}/versions/{versionId}/manifests")
    //DOCS
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
    //DOCS
    public ResponseEntity<?> getToolImages(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        List<Long> imagesResourceId = new ArrayList<>();
        for (MarketplaceItemImage image : tool.getImages()) {
            imagesResourceId.add(image.getManifestStoredResourceId());
        }
        
        return ResponseEntity.ok(imagesResourceId);
    }



}
