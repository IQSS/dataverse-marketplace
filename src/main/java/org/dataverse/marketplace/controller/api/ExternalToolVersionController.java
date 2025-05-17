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
            @PathVariable("toolId") Long toolId,
            @PathVariable("versionId") Long versionId) {

        ExternalToolVersion version = externalToolVersionService.getToolVersionById(versionId);
        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    /**
     * Method to update a specific external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE 
      + " or (" + ApplicationRoles.EDITOR_ROLE + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @CacheEvict(value = "externalTools", allEntries = true)
    @PutMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.UpdateVersionByIdDoc
    public ResponseEntity<?> updateVersionById(
            @PathVariable("toolId") Long toolId,
            @PathVariable("versionId") Long versionId,
            @Valid @RequestBody UpdateVersionRequest updateToolVersionRequest) {

        ExternalToolVersion version = externalToolVersionService.getToolVersionById(versionId);
        version.setDataverseMinVersion(updateToolVersionRequest.getDvMinVersion());
        version.setReleaseNote(updateToolVersionRequest.getReleaseNote());
        version.setVersion(updateToolVersionRequest.getVersion());

        externalToolVersionService.updateToolVersion(version);

        return ResponseEntity.ok(new ExternalToolVersionDTO(version));
    }

    /**
     * Method to delete a specific external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE 
      + " or (" + ApplicationRoles.EDITOR_ROLE + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @CacheEvict(value = "externalTools", allEntries = true)
    @DeleteMapping("/{toolId}/versions/{versionId}")
    @ExternalToolVersionsAPIDocs.DeleteExternalToolVersionByIdDoc
    public ResponseEntity<?> deleteVersionById(
            @PathVariable("toolId") Long toolId,
            @PathVariable("versionId") Long versionId) {

        try{
            if (externalToolVersionService.getVersionCount(toolId) > 1) {
                ExternalToolVersion version = externalToolVersionService.getToolVersionById(versionId);
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
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE 
      + " or (" + ApplicationRoles.EDITOR_ROLE + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @CacheEvict(value = "externalTools", allEntries = true)
    @PostMapping(path = "/{toolId}/versions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolVersionsAPIDocs.AddExternalToolVersionDoc
    public ResponseEntity<?> addNewExternalToolVersion(
            @PathVariable("toolId") Long toolId,
            AddVersionRequest addVersionRequest) { 
        
        ExternalTool tool = externalToolService.getToolById(toolId);
        if (tool == null) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        } else {

            try {
                ExternalTool externalTool = externalToolService.getToolById(toolId);
                ExternalToolVersion newVersion = externalToolVersionService.addToolVersion(addVersionRequest, externalTool);
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
            @PathVariable("toolId") Long toolId) {

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


    // manifest related methods

    
    /**
     * Method to retrieve all manifests of an external tool version
     */
    @GetMapping("/{toolId}/versions/{versionId}/manifests")
    @ExternalToolVersionsAPIDocs.GetVersionManifestsDoc
    public ResponseEntity<?> getVersionManifestsById(
        @PathVariable("toolId") Long toolId,
        @PathVariable("versionId") Long versionId) {

        ExternalToolVersion version = externalToolVersionService.getToolVersionById(versionId);
        ExternalToolVersionDTO versionDTO = new ExternalToolVersionDTO(version);
        return ResponseEntity.ok(versionDTO.getManifestSet());
    }

  
    /**
     * Method to update the manifest metadata of an external tool version
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @CacheEvict(value = "externalTools", allEntries = true)
    @PutMapping(path = "/{toolId}/versions/{versionId}/manifest")
    @ExternalToolVersionsAPIDocs.UpdateVersionManifestDoc
    public ResponseEntity<?> updateVersionManifest(
            @PathVariable("toolId") Long toolId,
            @PathVariable("versionId") Long versionId,
            @Valid @RequestBody ExternalToolManifestDTO manifestDTO) throws IOException {

        try {
            ExternalToolVersion version = externalToolVersionService.getToolVersionById(versionId);
            ExternalToolVersion reversion = externalToolVersionService.updateVersionManifest(version, manifestDTO);
            return ResponseEntity.ok(new ExternalToolVersionDTO(reversion));
        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error updating manifest metadata",
                    String.format("An error occurred while updating the manifest metadta for version ID %d.", versionId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

}
