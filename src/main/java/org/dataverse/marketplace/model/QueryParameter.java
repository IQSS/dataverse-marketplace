package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "query_parameter")
public class QueryParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_parameter_id_seq")
    @SequenceGenerator(name = "query_parameter_id_seq", sequenceName = "query_parameter_id_seq", allocationSize = 1)
    private Integer id;

    String key;
    String value;

    @ManyToOne
    @JoinColumn(name = "manifest_id", nullable = false)
    private ExternalToolManifest manifest;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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
