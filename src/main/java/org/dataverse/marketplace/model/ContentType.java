package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "content_type")
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_type_id_seq")
    @SequenceGenerator(name = "content_type_id_seq", sequenceName = "content_type_id_seq", allocationSize = 1)
    private Integer id;

    String contentType;

    @ManyToOne
    @JoinColumn(name = "manifest_id", nullable = false)
    private ExternalToolManifest manifest;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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
