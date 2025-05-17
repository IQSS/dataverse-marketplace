package org.dataverse.marketplace.payload;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request to add a new version of an existing external tool")
public class AddVersionRequest implements Serializable{

    @Schema(description = "A brief note about the current release of the existing external tool.", 
        example = "This release includes a new feature that allows you to ask questions to an LLM.")
    private String releaseNote;
    
    @Schema(description = "Version descriptor of the new version of the existing external tool", 
        example = "\"1.0\"")
    private String version;

    @Schema(description = "Minimum version of Dataverse that this version of the existing external tool is compatible with", 
        example = "\"6.0\"")
    @NotEmpty
    private String dvMinVersion;
/* 
    @Schema(description = "Manifest files for this version", 
        example = "[\"ask-the-data.json\"]",
        type = "array",
        contentMediaType = "application/octet-stream",
        implementation = MultipartFile[].class)
    @NotEmpty
    private List<MultipartFile> jsonData;
*/
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
/* 
    public List<MultipartFile> getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(List<MultipartFile> jsonData) {
        this.jsonData = jsonData;
    }
*/

    @Override
    public String toString() {
        return "{" +
            " releaseNote='" + getReleaseNote() + "'" +
            ", version='" + getVersion() + "'" +
            ", dvMinVersion='" + getDvMinVersion() + "'" +
           // ", jsonData='" + getJsonData() + "'" +
            "}";
    }

}
