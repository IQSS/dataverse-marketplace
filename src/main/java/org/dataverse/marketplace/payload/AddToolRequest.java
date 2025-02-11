package org.dataverse.marketplace.payload;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request to add a new external tool")
public class AddToolRequest {

    @Schema(description = "Name of the external tool", 
        example = "Ask the data")
    @NotEmpty
    String name;

    @Schema(description = "Description of the external tool", 
        example = "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.")    
    @NotEmpty
    String description;

    @Schema(description = "A brief not about the current release of the external tool.")
    String releaseNote;
    
    @Schema(description = "Version of the external tool", 
        example = "1.0.0")
    String version;

    @Schema(description = "Minimum version of Dataverse that the external tool is compatible with", 
        example = "6.0")
    @NotEmpty
    String dvMinVersion;

    @Schema(description = "Tool JSON descriptor file", 
        example = "ask-the-data.json")
    @NotEmpty
    MultipartFile jsonData;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public MultipartFile getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(MultipartFile jsonData) {
        this.jsonData = jsonData;
    }
    

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", releaseNote='" + getReleaseNote() + "'" +
            ", version='" + getVersion() + "'" +
            ", dvMinVersion='" + getDvMinVersion() + "'" +
            ", jsonData='" + getJsonData().getOriginalFilename() + "'" +
            "}";
    }

   

}
