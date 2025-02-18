package org.dataverse.marketplace.payload;

import java.util.ArrayList;
import java.util.List;
import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.samples.ExternalToolVersionSamples;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A representation of a version of an external tool", 
        name = "Version")
public class ExternalToolVersionDTO {

    @Schema(description = "The unique identifier of the external tool version", 
            example = "1")
    private Integer id;

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
            implementation = ExternalToolManifestDTO[].class,              
            example = ExternalToolVersionSamples.EXTERNAL_TOOL_VERSION_MANIFESTS_SAMPLE)
    private List<ExternalToolManifestDTO> manifests;
    
    public ExternalToolVersionDTO(ExternalToolVersion version) {

        this.id = version.getId();
        this.version = version.getVersionMetadata().getVersion();
        this.releaseNote = version.getVersionMetadata().getReleaseNote();
        this.dataverseMinVersion = version.getVersionMetadata().getDataverseMinVersion();

        this.manifests = new ArrayList<>();

        for (ExternalToolManifest manifest : version.getManifests()){
            ExternalToolManifestDTO manifestDTO = new ExternalToolManifestDTO();
            manifestDTO.setManifestId(manifest.getManifestId());
            manifestDTO.setStoredResourceId(manifest.getManifestStoredResourceId());
            this.manifests.add(manifestDTO);
        }
        
    }

    /* Getters and Setters */

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<ExternalToolManifestDTO> getManifests() {
        return this.manifests;
    }

    public void setManifests(List<ExternalToolManifestDTO> manifests) {
        this.manifests = manifests;
    }
    
}
