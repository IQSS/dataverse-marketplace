package org.dataverse.marketplace.payload;

import java.io.Serializable;

import org.dataverse.marketplace.model.ExternalToolManifest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "External tool manifest data transfer object")
public class ExternalToolManifestDTO implements Serializable {

    @Schema(description = "The manifest ID", example = "1")
    public Integer manifestId;

    @Schema(description = "The stored resource ID", example = "1")
    public Long storedResourceId;

    public ExternalToolManifestDTO() {
    }

    public ExternalToolManifestDTO(ExternalToolManifest externalToolManifest) {
        this.manifestId = externalToolManifest.getManifestId();
        this.storedResourceId = externalToolManifest.getManifestStoredResourceId();
    }

    /* Getters and Setters */

    public Integer getManifestId() {
        return this.manifestId;
    }

    public void setManifestId(Integer manifestId) {
        this.manifestId = manifestId;
    }

    public Long getStoredResourceId() {
        return this.storedResourceId;
    }

    public void setStoredResourceId(Long storedResourceId) {
        this.storedResourceId = storedResourceId;
    }

}