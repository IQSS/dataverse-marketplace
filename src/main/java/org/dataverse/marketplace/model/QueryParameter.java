package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
public class QueryParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String key;
    String value;

    @ManyToOne
    @JoinColumn(name = "manifest_id", nullable = false)
    private ExternalToolManifest manifest;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExternalToolManifest getManifest() {
        return manifest;
    }

    public void setManifest(ExternalToolManifest manifest) {
        this.manifest = manifest;
    }

}
