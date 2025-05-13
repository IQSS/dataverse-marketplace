package org.dataverse.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "external_tool_type")
public class ExternalToolType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "external_tool_type_id_seq")
    @SequenceGenerator(name = "external_tool_type_id_seq", sequenceName = "external_tool_type_id_seq", allocationSize = 1)
    private Integer id;

    String type;

    @ManyToOne
    @JoinColumn(name = "manifest_id", nullable = false)
    private ExternalToolManifest manifest;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public ExternalToolManifest getManifest() {
        return manifest;
    }

    public void setManifest(ExternalToolManifest manifest) {
        this.manifest = manifest;
    }

}
