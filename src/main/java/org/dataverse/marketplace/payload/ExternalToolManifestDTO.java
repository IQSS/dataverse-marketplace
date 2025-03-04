package org.dataverse.marketplace.payload;

import java.io.Serializable;

import org.dataverse.marketplace.model.ExternalToolManifest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "External tool manifest data transfer object")
public class ExternalToolManifestDTO implements Serializable {

    @Schema(description = "The manifest ID", example = "1")
    public Integer manifestId;

    @Schema(description = "The manifest's stored resource ID", example = "1")
    public Long storedResourceId;

    @Schema(description = "The manifest's file name", example = "manifest.json")
    public String fileName;

    public ExternalToolManifestDTO() {
    }

    public ExternalToolManifestDTO(ExternalToolManifest externalToolManifest) {
        this.manifestId = externalToolManifest.getManifestId();
        this.storedResourceId = externalToolManifest.getManifestStoredResourceId();
        this.fileName = externalToolManifest.getStoredResource().getFileName();
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return "{" +
            " manifestId='" + getManifestId() + "'" +
            ", storedResourceId='" + getStoredResourceId() + "'" +
            ", fileName='" + getFileName() + "'" +
            "}";
    }


}