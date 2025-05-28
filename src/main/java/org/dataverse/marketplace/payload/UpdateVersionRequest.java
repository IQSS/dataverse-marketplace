package org.dataverse.marketplace.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "A representation of the update tool version request")
public class UpdateVersionRequest {

    @Schema(description = "Version name to be updated", 
        example = "\"1.0\"")
    String versionName;

    @Schema(description = "Version note to be updated", 
        example = "This version includes a new feature that allows you to ask questions to an LLM.")
    String versionNote;
    
    @Schema(description = "Minimum Dataverse version to be updated", 
        example = "\"6.0\"")
    @NotEmpty
    String dataverseMinVersion;

    /* Getters and Setters */

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionNote() {
        return this.versionNote;
    }

    public void setVersionNote(String versionNote) {
        this.versionNote = versionNote;
    }

    public String getDataverseMinVersion() {
        return this.dataverseMinVersion;
    }

    public void setDataverseMinVersion(String dataverseMinVersion) {
        this.dataverseMinVersion = dataverseMinVersion;
    }



    @Override
    public String toString() {
        return "{" +
            "versionName='" + getVersionName() + "'" +
            ", versionNote='" + getVersionNote() + "'" +
            ", dataverseMinVersion='" + getDataverseMinVersion() + "'" +
            "}";
    }



}
