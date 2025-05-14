package org.dataverse.marketplace.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "external_tool_version")
public class ExternalToolVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "external_tool_id")
    private ExternalTool externalTool;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.REMOVE)
    private List<ExternalToolManifest> manifests = new ArrayList<ExternalToolManifest>();

    private String releaseNote;

    @Column(name = "item_version")
    private String version;

    @Column(name = "dv_min_version")
    private String dataverseMinVersion;

    /* Getters and Setters */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalTool getExternalTool() {
        return this.externalTool;
    }

    public void setExternalTool(ExternalTool externalTool) {
        this.externalTool = externalTool;
    }

    public List<ExternalToolManifest> getManifests() {
        return this.manifests;
    }

    public void setManifests(List<ExternalToolManifest> manifests) {
        this.manifests = manifests;
    }

    public String getReleaseNote() {
        return this.releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDataverseMinVersion() {
        return this.dataverseMinVersion;
    }

    public void setDataverseMinVersion(String dataverseMinVersion) {
        this.dataverseMinVersion = dataverseMinVersion;
    }

}
