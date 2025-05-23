package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.samples.ExternalToolVersionSamples;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A representation of a version of an external tool", name = "Version")
public class ExternalToolVersionDTO implements Serializable {

    @Schema(description = "The unique identifier of the external tool version", example = "1")
    private Long id;

    @Schema(description = "The version name of the external tool", example = "\"1.0\"")
    private String versionName;

    @Schema(description = "The version note of the external tool", example = "This is a release note")
    private String versionNote;

    @Schema(description = "The minimum version of Dataverse required for the external tool", example = "\"6.0\"")
    private String dataverseMinVersion;

    @Schema(description = "The manifest metadata of the external tool", implementation = ExternalToolManifestDTO.class, example = ExternalToolVersionSamples.EXTERNAL_TOOL_VERSION_MANIFESTS_SAMPLE)
    private ExternalToolManifestDTO manifest;

    public ExternalToolVersionDTO() {
    }

    public ExternalToolVersionDTO(ExternalToolVersion version) {

        this.id = version.getId();
        this.versionName = version.getVersionName();
        this.versionNote = version.getVersionNote();
        this.dataverseMinVersion = version.getDataverseMinVersion();

        this.manifest = new ExternalToolManifestDTO(version);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public Set<ExternalToolManifestDTO> getManifestSet() {

        Set<ExternalToolManifestDTO> manifestDTOs = new HashSet<ExternalToolManifestDTO>();

        if (ExternalToolManifestDTO.isValidManifest(this.manifest)) {

            if (this.manifest.getContentTypes() != null && !this.manifest.getContentTypes().isEmpty()) {
                for (String contentType : this.manifest.getContentTypes()) {
                    ExternalToolManifestDTO manifestDTO = new ExternalToolManifestDTO(manifest, contentType);
                    manifestDTOs.add(manifestDTO);
                }
            } else {
                manifestDTOs.add(this.manifest);
            }
        }

        return manifestDTOs;
    }

    /* Getters and Setters */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExternalToolVersionDTO{");
        sb.append("id=").append(id);
        sb.append(", versionName='").append(versionName).append('\'');
        sb.append(", versionNote='").append(versionNote).append('\'');
        sb.append(", dataverseMinVersion='").append(dataverseMinVersion).append('\'');
        sb.append(", manifest=").append(manifest != null ? manifest.toString() : "null");
        sb.append('}');
        return sb.toString();
    }

}
