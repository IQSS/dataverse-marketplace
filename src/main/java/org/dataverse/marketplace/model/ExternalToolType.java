package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
public class ExternalToolType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String type;

    @ManyToOne
    @JoinColumn(name = "external_tool_version_id", nullable = false)
    private ExternalToolVersion externalToolVersion;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return externalToolVersion;
    }

    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
    }

}
