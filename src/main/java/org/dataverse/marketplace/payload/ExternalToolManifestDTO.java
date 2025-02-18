package org.dataverse.marketplace.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExternalToolManifestDTO {

    @Schema(description = "The manifest ID", example = "1")
    public Integer manifestId;

    @Schema(description = "The stored resource ID", example = "1")
    public Long storedResourceId;

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