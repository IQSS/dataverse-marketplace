package org.dataverse.marketplace.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.annotations.ExternalToolVersionsAPIDocs;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.security.ApplicationRoles;
import org.dataverse.marketplace.service.ExternalToolService;
import org.dataverse.marketplace.service.ExternalToolVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tools")
public class ExternalToolVersionController {

    @Autowired
    private ExternalToolService externalToolService;

    @Autowired
    private ExternalToolVersionService externalToolVersionService;

    /**
     * Method to retrieve a specific external tool version
     */
    @GetMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.GetExternalToolVersionByIdDoc
    public ResponseEntity<?> getVersionById(
            @PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId) {

        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);
        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    /**
     * Method to update a specific external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @CacheEvict(value = "externalTools", allEntries = true)
    @PutMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.UpdateVersionByIdDoc
    public ResponseEntity<?> updateVersionById(
            @PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId,
            @Valid @RequestBody ToolVersionMetadataUpdateRequest updateToolVersionRequest) {

        System.out.println(updateToolVersionRequest);
        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);

        VersionMetadata metadata = version.getVersionMetadata();
        metadata.setDataverseMinVersion(updateToolVersionRequest.getDvMinVersion());
        metadata.setReleaseNote(updateToolVersionRequest.getReleaseNote());
        metadata.setVersion(updateToolVersionRequest.getVersion());

        externalToolVersionService.updateToolVersion(version);

        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    /**
     * Method to delete a specific external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @CacheEvict(value = "externalTools", allEntries = true)
    @DeleteMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.DeleteExternalToolVersionByIdDoc
    public ResponseEntity<?> deleteVersionById(
            @PathVariable("toolId") Integer toolId,
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

    /**
     * Method to add a new version to an existing external tool
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @CacheEvict(value = "externalTools", allEntries = true)
    @PostMapping(path = "/{toolId}/versions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolVersionsAPIDocs.AddExternalToolVersionDoc
    public ResponseEntity<?> addNewExternalToolVersion(
            @PathVariable("toolId") Integer toolId,
            @Valid @RequestBody AddVersionRequest addVersionRequest) { 
        
        ExternalTool tool = externalToolService.getToolById(toolId);
        if (tool == null) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        } else {

            try {
                ExternalToolVersion newVersion = externalToolVersionService.addToolVersion(addVersionRequest, toolId);
                return ResponseEntity.ok(new ExternalToolVersionDTO(newVersion));
            } catch (IOException e) {
                ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error adding version",
                        String.format("An error occurred while adding a new version for the tool with ID %d.", toolId));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
            }
        }
                
        
    }

    /**
     * Method to retrieve all external tool versions     
     */ 
    @GetMapping("/{toolId}/versions")
    @ExternalToolVersionsAPIDocs.GetExternalToolVersionsByToolIdDoc
    public ResponseEntity<?> getVersionsToolByToolId(
            @PathVariable("toolId") Integer toolId) {

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

    /**
     * Method to add manifests to an existing external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @CacheEvict(value = "externalTools", allEntries = true)
    @ExternalToolVersionsAPIDocs.AddVersionManifestDoc
    @PostMapping(path = "/{toolId}/versions/{versionId}/manifests", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addVersionManifest(
            @PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId,
            @RequestPart List<MultipartFile> jsonData) throws IOException {
        
        ExternalToolVersion version = externalToolService.getToolVersionById(toolId, versionId);
        externalToolVersionService.addVersionManifests(version, jsonData);
        ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                "Manifests added",
                String.format("The manifests were added to the version with ID %d.", versionId));
        
        return ResponseEntity.ok(messageResponse);
    }
    
    /**
     * Method to retrieve all manifests of an external tool version
     */
    @GetMapping("/{toolId}/versions/{versionId}/manifests")
    @ExternalToolVersionsAPIDocs.GetVersionManifestDoc
    public ResponseEntity<?> getVersionManifestsById(
        @PathVariable("toolId") Integer toolId,
        @PathVariable("versionId") Integer versionId) {

        List<ExternalToolManifest> manifests = externalToolService.getToolManifests(toolId, versionId);
        List<ExternalToolManifestDTO> manifestStoredResourceId = new ArrayList<>();
        for (ExternalToolManifest manifest : manifests) {
            ExternalToolManifestDTO manifestDTO = new ExternalToolManifestDTO(manifest);
            manifestStoredResourceId.add(manifestDTO);
        }
        return ResponseEntity.ok(manifestStoredResourceId);
    }

    /**
     * Method to delete a specific manifest of an external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @CacheEvict(value = "externalTools", allEntries = true)
    @ExternalToolVersionsAPIDocs.DeleteVersionManifestDoc
    @DeleteMapping("/{toolId}/versions/{versionId}/manifests/{manifestId}")
    public ResponseEntity<?> deleteVersionManifest(
            @PathVariable("toolId") Integer toolId,
            @PathVariable("versionId") Integer versionId,
            @PathVariable("manifestId") Integer manifestId) {
        
        try {
            
            externalToolVersionService.deleteToolManifest(toolId, versionId, manifestId);
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                    "Manifest deleted",
                    String.format("The manifest with ID %d was deleted.", manifestId));
            return ResponseEntity.ok(messageResponse);

        } catch (IOException e) {
            
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error deleting manifest",
                    String.format("An error occurred while deleting the manifest with ID %d.", manifestId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

}
