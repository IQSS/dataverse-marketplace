package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;


@Schema(description = "Request to add a new external tool")
public class AddToolRequest implements Serializable{

    @Schema(description = "Name of the external tool", 
        example = "Ask the data")
    @NotEmpty
    private String name;

    @Schema(description = "Description of the external tool", 
        example = "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.")    
    @NotEmpty
    private String description;

    @Schema(description = "A brief note about the current version of the new external tool.", 
        example = "This release includes a new feature that allows you to ask questions to an LLM.")
    private String releaseNote;
    
    @Schema(description = "Version descriptor of the new external tool", 
        example = "\"1.0\"")
    private String version;

    @Schema(description = "Minimum version of Dataverse that the new external tool is compatible with", 
        example = "\"6.0\"")
    @NotEmpty
    private String dvMinVersion;

    @Schema(description = "Manifest files for the default versionof the new external tool", 
            type = "array",
            contentMediaType = "multipart/form-data",
            implementation = MultipartFile[].class,
            nullable = false,
            example = "[\"file.json\"]")
    @NotEmpty
    private List<MultipartFile> jsonData;

    @Schema(description = "Image files for the new external tool", 
            type = "array",
            contentMediaType = "multipart/form-data",
            implementation = MultipartFile[].class,
            nullable = true,
            defaultValue = "[]",
            example = "[\"image1.png\", \"image2.png\"]")
    private  List<MultipartFile> itemImages;

    /* Getters and Setters */

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

    public List<MultipartFile> getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(List<MultipartFile> jsonData) {
        this.jsonData = jsonData;
    }

    public List<MultipartFile> getItemImages() {
        return this.itemImages;
    }

    public void setItemImages(List<MultipartFile> itemImages) {
        this.itemImages = itemImages;
    }


    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", releaseNote='" + getReleaseNote() + "'" +
            ", version='" + getVersion() + "'" +
            ", dvMinVersion='" + getDvMinVersion() + "'" +
            ", jsonData='" + getJsonData() + "'" +
            ", itemImages='" + getItemImages() + "'" +
            "}";
    }


}
