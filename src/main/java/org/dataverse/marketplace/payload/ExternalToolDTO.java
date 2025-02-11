package org.dataverse.marketplace.payload;

import org.dataverse.marketplace.model.ExternalTool;

public class ExternalToolDTO {

    private String name;
    private String description;
    private String releaseNote;
    private String dataverseMinVersion;
    private String version;

    public ExternalToolDTO() {
    }

    public ExternalToolDTO(String name, String description, String releaseNote, String dataverseMinVersion, String version) {
        this.name = name;
        this.description = description;
        this.releaseNote = releaseNote;
        this.dataverseMinVersion = dataverseMinVersion;
        this.version = version;
    }

    public ExternalToolDTO(ExternalTool externalToolDTO) {

        // this.releaseNote = externalToolDTO.getReleaseNote();
        // this.dataverseMinVersion = externalToolDTO.getDataverseMinVersion();
        // this.version = externalToolDTO.getVersion();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getDataverseMinVersion() {
        return dataverseMinVersion;
    }

    public void setDataverseMinVersion(String dataverseMinVersion) {
        this.dataverseMinVersion = dataverseMinVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
