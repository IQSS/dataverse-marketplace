package org.dataverse.marketplace.payload;

import java.util.ArrayList;
import java.util.List;
import org.dataverse.marketplace.model.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A representation of a version of an external tool", 
        name = "Version")
public class ExternalToolVersionDTO {

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
    
    public ExternalToolVersionDTO(ExternalToolVersion version) {

        this.version = version.getVersionMetadata().getVersion();
        this.releaseNote = version.getVersionMetadata().getReleaseNote();
        this.dataverseMinVersion = version.getVersionMetadata().getDataverseMinVersion();

        this.manifestStoredResourceId = new ArrayList<>();

        for (ExternalToolManifest manifest : version.getManifests()){
            this.manifestStoredResourceId.add(manifest.getManifestStoredResourceId());
        }
    }

    /* Getters and Setters */

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
