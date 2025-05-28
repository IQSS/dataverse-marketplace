package org.dataverse.marketplace.payload;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request to add a new version of an existing external tool")
public class AddVersionRequest implements Serializable{

    @Schema(description = "Version name for the new version of the external tool.", 
        example = "\"1.0\"")
    String versionName;

    @Schema(description = "Version note for the new version of the external tool.", 
        example = "This version includes a new feature that allows you to ask questions to an LLM.")
    String versionNote;
    
    @Schema(description = "Minimum Dataverse version compatible with this version of the tool.", 
        example = "\"6.0\"")
    @NotEmpty
    private String dataverseMinVersion;

    @Schema(description = "Manifest metadata for this new version")
    private ExternalToolManifestDTO manifest;

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

    public ExternalToolManifestDTO getManifest() {
        return this.manifest;
    }
    public void setManifest(ExternalToolManifestDTO manifest) {
        this.manifest = manifest;
    }    


    @Override
    public String toString() {
        return "{" +
            "versionName='" + getVersionName() + "'" +
            ", versionNote='" + getVersionNote() + "'" +
            ", dataverseMinVersion='" + getDataverseMinVersion() + "'" +
            ", manifest='" + getManifest() + "'" +
            "}";
    }

}
