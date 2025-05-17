package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String contentType;

    @ManyToOne
    @JoinColumn(name = "external_tool_version_id", nullable = false)
    private ExternalToolVersion externalToolVersion;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return externalToolVersion;
    }
    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
    }

}
