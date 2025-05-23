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

    @Schema(description = "Version descriptor of the new version of the existing external tool", 
        example = "\"1.0\"")
    String versionName;

    @Schema(description = "A brief note about this version of the external tool.", 
        example = "This version includes a new feature that allows you to ask questions to an LLM.")
    String versionNote;
    
    @Schema(description = "Minimum version of Dataverse that this version of the existing external tool is compatible with", 
        example = "\"6.0\"")
    @NotEmpty
    private String dataverseMinVersion;

    @Schema(description = "Manifest metadata for this version") 
    private ExternalToolManifestDTO manifest;

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
            ", versionName='" + getVersionName() + "'" +
            ", versionNote='" + getVersionNote() + "'" +
            ", dataverseMinVersion='" + getDataverseMinVersion() + "'" +
            ", manifest='" + getManifest() + "'" +
            ", itemImages='" + getItemImages() + "'" +
            "}";
    }

}    
