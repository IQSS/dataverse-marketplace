package org.dataverse.marketplace.payload;

import java.util.ArrayList;
import java.util.List;
import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.samples.ExternalToolVersionSamples;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExternalTool",
        description = "A representation of an external tool")
public class ExternalToolDTO {

    @Schema(description = "The unique identifier of the external tool", 
            example = "1")
    private Integer id;

    @Schema(description = "The name of the external tool", 
            example = "My External Tool")
    private String name;

    @Schema(description = "The description of the external tool", 
            example = "This is a description of my external tool")
    private String description;

    @Schema(description = "The list of versions of the external tool", 
            example = ExternalToolVersionSamples.EXTERNAL_TOOL_VERSIONS_LIST_SAMPLE)
    private List<ExternalToolVersionDTO> versions;
    
    @Schema(description = "The list of storage id for the images of the external tool", 
            implementation = Long[].class,
            example = "[1, 2, 3]")
    private List<Long> imagesResourceId;

    public ExternalToolDTO(ExternalTool externalTool) {
        id = externalTool.getId();
        name = externalTool.getName();
        description = externalTool.getDescription();

        this.versions = new ArrayList<>();
        for (ExternalToolVersion version : externalTool.getExternalToolVersions()) {
            versions.add(new ExternalToolVersionDTO(version));
        }

        this.imagesResourceId = new ArrayList<>();
        for(MarketplaceItemImage image : externalTool.getImages()){
            this.imagesResourceId.add(image.getImageStoredResourceId());
        }
    }

    /* Getters and Setters */

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<ExternalToolVersionDTO> getVersions() {
        return this.versions;
    }

    public void setVersions(List<ExternalToolVersionDTO> versions) {
        this.versions = versions;
    }

    public List<Long> getImages() {
        return this.imagesResourceId;
    }

    public void setImages(List<Long> imagesResourceId) {
        this.imagesResourceId = imagesResourceId;
    }

    
}
