package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "content_type")
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String contentType;

    @ManyToOne
    @JoinColumn(name = "manifest_id", nullable = false)
    private ExternalToolManifest manifest;

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

    public ExternalToolManifest getManifest() {
        return manifest;
    }

    public void setManifest(ExternalToolManifest manifest) {
        this.manifest = manifest;
    }

}
