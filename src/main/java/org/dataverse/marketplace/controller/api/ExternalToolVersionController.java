package org.dataverse.marketplace.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.VersionMetadata;
import org.dataverse.marketplace.openapi.annotations.ExternalToolVersionsAPIDocs;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.payload.ExternalToolVersionDTO;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.ToolVersionMetadataUpdateRequest;
import org.dataverse.marketplace.service.ExternalToolService;
import org.dataverse.marketplace.service.ExternalToolVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tools")
public class ExternalToolVersionController {

    @Autowired
    private ExternalToolService externalToolService;

    @Autowired
    private ExternalToolVersionService externalToolVersionService;

    @Hidden
    @GetMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.GetExternalToolVersionByIdDoc
    public ResponseEntity<?> getVersionById(@PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId) {

        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);
        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    @PutMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.UpdateVersionByIdDoc
    public ResponseEntity<?> updateVersionById(@PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId,
            @Valid @RequestBody ToolVersionMetadataUpdateRequest updateToolVersionRequest) {

        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);

        VersionMetadata metadata = version.getVersionMetadata();
        metadata.setDataverseMinVersion(updateToolVersionRequest.getDvMinVersion());
        metadata.setReleaseNote(updateToolVersionRequest.getReleaseNote());
        metadata.setVersion(updateToolVersionRequest.getVersion());

        externalToolVersionService.updateToolVersion(version);

        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    @Hidden
    @DeleteMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.DeleteExternalToolVersionByIdDoc
    public ResponseEntity<?> deleteVersionById(@PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId) {
        try{
            if (externalToolVersionService.getVersionCount(toolId) > 1) {
                ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);
                externalToolVersionService.deleteToolVersion(version);
                ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                        "Version deleted",
                        String.format("Version with ID %d was deleted.", version.getId()));
                return ResponseEntity.ok(messageResponse);
            } else {
                ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                        "Can't delete the only version",
                        String.format("The version with ID %d can't be deleted.", toolId));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
            }
        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error deleting version",
                    String.format("An error occurred while deleting the version with ID %d.", toolId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }

    }

    @PostMapping(path = "/{toolId}/versions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolVersionsAPIDocs.AddExternalToolVersionDoc
    public ResponseEntity<?> addNewExternalToolVersion(
            @PathVariable("toolId") Integer toolId,
            @Valid AddVersionRequest addVersionRequest) {  
                
        try {
            return ResponseEntity.ok(
                externalToolVersionService.addToolVersion(addVersionRequest, toolId));
        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error adding version",
                    String.format("An error occurred while adding a new version for the tool with ID %d.", toolId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

    /**
     * Method to retrieve all external tool versions     
     */
    @Hidden
    @GetMapping("/{toolId}/versions")
    @ExternalToolVersionsAPIDocs.GetExternalToolVersionsByToolIdDoc
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

}
