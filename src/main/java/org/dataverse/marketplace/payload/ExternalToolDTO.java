package org.dataverse.marketplace.payload;

import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolVersion;

public class ExternalToolDTO {

    private String name;
    private String description;
    private List<VersionDTO> versions;

    public ExternalToolDTO(ExternalTool externalTool) {

        name = externalTool.getName();
        description = externalTool.getDescription();

        ArrayList<VersionDTO> versions = new ArrayList<>();
        for (ExternalToolVersion version : externalTool.getExternalToolVersions()) {
            versions.add(new VersionDTO(version));
        }
        this.versions = versions;

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

    public class VersionDTO {

        private String version;
        private String releaseNote;
        private String dataverseMinVersion;
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
