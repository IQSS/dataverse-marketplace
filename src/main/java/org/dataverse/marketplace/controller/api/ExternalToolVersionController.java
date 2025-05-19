package org.dataverse.marketplace.controller.api;

import java.io.IOException;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.annotations.ExternalToolVersionsAPIDocs;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.security.ApplicationRoles;
import org.dataverse.marketplace.service.ExternalToolVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/versions")
public class ExternalToolVersionController {

    @Autowired
    private ExternalToolVersionService externalToolVersionService;

    /**
     * Method to retrieve a specific external tool version
     */
    @GetMapping("/{versionId}")
    @ExternalToolVersionsAPIDocs.GetExternalToolVersionByIdDoc
    public ResponseEntity<?> getVersionById(
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
    @PutMapping("/{versionId}")
    @ExternalToolVersionsAPIDocs.UpdateVersionByIdDoc
    public ResponseEntity<?> updateVersionById(
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
    @DeleteMapping("/{versionId}")
    @ExternalToolVersionsAPIDocs.DeleteExternalToolVersionByIdDoc
    public ResponseEntity<?> deleteVersionById(
            @PathVariable("versionId") Long versionId) {

        try{
            ExternalToolVersion version = externalToolVersionService.getToolVersionById(versionId);

            if (externalToolVersionService.getVersionCount(version.getExternalTool().getId()) > 1) {
                externalToolVersionService.deleteToolVersion(version);
                ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                        "Version deleted",
                        String.format("Version with ID %d was deleted.", versionId));
                return ResponseEntity.ok(messageResponse);
            } else {
                ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                        "Can't delete the only version",
                        String.format("The version with ID %d can't be deleted.", versionId));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
            }
        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error deleting version",
                    String.format("An error occurred while deleting the version with ID %d.", versionId));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }

    }

    //*******************************/
    // manifest related methods
    //*******************************/

    /**
     * Method to retrieve all manifests of an external tool version
     */
    @GetMapping("{versionId}/manifests")
    @ExternalToolVersionsAPIDocs.GetVersionManifestsDoc
    public ResponseEntity<?> getVersionManifestsById(
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
    @PutMapping(path = "{versionId}/manifest")
    @ExternalToolVersionsAPIDocs.UpdateVersionManifestDoc
    public ResponseEntity<?> updateVersionManifest(
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
