package org.dataverse.marketplace.payload;

import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.MarketplaceItemImage;
import org.dataverse.marketplace.openapi.samples.ExternalToolSamples;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExternalTool",
        description = "A representation of an external tool")
public class ExternalToolDTO {

    @Schema(description = "The name of the external tool", 
            example = "My External Tool")
    private String name;

    @Schema(description = "The description of the external tool", 
            example = "This is a description of my external tool")
    private String description;

    @Schema(description = "The list of versions of the external tool", 
            example = ExternalToolSamples.EXTERNAL_TOOL_VERSIONS_LIST_SAMPLE)
    private List<VersionDTO> versions;
    
    @Schema(description = "The list of storage id for the images of the external tool", 
            implementation = Long[].class,
            example = "[1, 2, 3]")
    private List<Long> imagesResourceId;

    public ExternalToolDTO(ExternalTool externalTool) {

        name = externalTool.getName();
        description = externalTool.getDescription();

        this.versions = new ArrayList<>();
        for (ExternalToolVersion version : externalTool.getExternalToolVersions()) {
            versions.add(new VersionDTO(version));
        }

        this.imagesResourceId = new ArrayList<>();
        for(MarketplaceItemImage image : externalTool.getImages()){
            this.imagesResourceId.add(image.getManifestStoredResourceId());
        }
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

    public List<VersionDTO> getVersions() {
        return this.versions;
    }

    public void setVersions(List<VersionDTO> versions) {
        this.versions = versions;
    }

    public List<Long> getImages() {
        return this.imagesResourceId;
    }

    public void setImages(List<Long> imagesResourceId) {
        this.imagesResourceId = imagesResourceId;
    }

    @Schema(description = "A representation of a version of an external tool", 
        name = "Version")
    public class VersionDTO {

        @Schema(description = "The version of the external tool", 
                example = "\"1.0\"")
        private String version;

        @Schema(description = "The release note of the external tool", 
                example = "This is a release note")
        private String releaseNote;

        @Schema(description = "The minimum version of Dataverse required for the external tool", 
                example = "\"6.0\"")
        private String dataverseMinVersion;

        @Schema(description = "The list of storage id for the manifests of the external tool",   
                implementation = Long[].class,              
                example = "[1, 2, 3]")
        private List<Long> manifestStoredResourceId;
        
        public VersionDTO(ExternalToolVersion version) {

            this.version = version.getVersionMetadata().getVersion();
            this.releaseNote = version.getVersionMetadata().getReleaseNote();
            this.dataverseMinVersion = version.getVersionMetadata().getDataverseMinVersion();

            this.manifestStoredResourceId = new ArrayList<>();

            for (ExternalToolManifest manifest : version.getManifests()){
                this.manifestStoredResourceId.add(manifest.getManifestStoredResourceId());
            }
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getReleaseNote() {
            return this.releaseNote;
        }

        public void setReleaseNote(String releaseNote) {
            this.releaseNote = releaseNote;
        }

        public String getDataverseMinVersion() {
            return this.dataverseMinVersion;
        }

        public void setDataverseMinVersion(String dataverseMinVersion) {
            this.dataverseMinVersion = dataverseMinVersion;
        }

        public List<Long> getManifestStoredResourceId() {
            return this.manifestStoredResourceId;
        }

        public void setManifestStoredResourceId(List<Long> manifestStoredResourceId) {
            this.manifestStoredResourceId = manifestStoredResourceId;
        }
        
    }
}
