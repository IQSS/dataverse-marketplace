package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
public class AuxFilesExist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String formatTag;
    String formatVersion;

    @ManyToOne
    @JoinColumn(name = "external_tool_version_id", nullable = false)
    private ExternalToolVersion externalToolVersion;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return externalToolVersion;
    }

    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
    }

    public String getFormatTag() {
        return formatTag;
    }
    public void setFormatTag(String formatTag) {
        this.formatTag = formatTag;
    }

    public String getFormatVersion() {
        return formatVersion;
    }
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }
}
