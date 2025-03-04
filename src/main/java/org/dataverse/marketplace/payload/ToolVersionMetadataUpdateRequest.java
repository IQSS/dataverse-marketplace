package org.dataverse.marketplace.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "A representation of the update tool version request")
public class ToolVersionMetadataUpdateRequest {

    @Schema(description = "A brief not about the current release of the external tool.", 
        example = "This release includes a new feature that allows you to ask questions to an LLM.")
    String releaseNote;
    
    @Schema(description = "Version of the external tool", 
        example = "\"1.0\"")
    String version;

    @Schema(description = "Minimum version of Dataverse that the external tool is compatible with", 
        example = "\"6.0\"")
    @NotEmpty
    String dvMinVersion;

    /* Getters and Setters */


    public String getReleaseNote() {
        return this.releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDvMinVersion() {
        return this.dvMinVersion;
    }

    public void setDvMinVersion(String dvMinVersion) {
        this.dvMinVersion = dvMinVersion;
    }


    @Override
    public String toString() {
        return "{" +
            " releaseNote='" + getReleaseNote() + "'" +
            ", version='" + getVersion() + "'" +
            ", dvMinVersion='" + getDvMinVersion() + "'" +
            "}";
    }



}
