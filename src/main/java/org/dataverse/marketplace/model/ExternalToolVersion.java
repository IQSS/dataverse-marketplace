package org.dataverse.marketplace.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@IdClass(ExternalToolVersion.ExternalToolVersionId.class)
@Table(name = "external_tool_version", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id", "mkt_item_id" })
})
public class ExternalToolVersion implements Serializable {

    @Id
    Integer id;

    @Id
    @Column(name = "mkt_item_id")
    private Integer mkItemId;

    @ManyToOne
    @JoinColumn(name = "mkt_item_id", insertable = false, updatable = false)
    private ExternalTool externalTool;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "v_metadata_id")
    private VersionMetadata versionMetadata;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.REMOVE)
    private List<ExternalToolManifest> manifests = new ArrayList<ExternalToolManifest>();
    

    /* Getters and Setters */


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMkItemId() {
        return this.mkItemId;
    }

    public void setMkItemId(Integer mkItemId) {
        this.mkItemId = mkItemId;
    }

    public ExternalTool getExternalTool() {
        return this.externalTool;
    }

    public void setExternalTool(ExternalTool externalTool) {
        this.externalTool = externalTool;
    }

    public VersionMetadata getVersionMetadata() {
        return this.versionMetadata;
    }

    public void setVersionMetadata(VersionMetadata versionMetadata) {
        this.versionMetadata = versionMetadata;
    }

    public List<ExternalToolManifest> getManifests() {
        return this.manifests;
    }

    public void setManifests(List<ExternalToolManifest> manifests) {
        this.manifests = manifests;
    }
    
    @Embeddable
    public static class ExternalToolVersionId implements Serializable {

        private Integer id;
        private Integer mkItemId;

        // Default constructor
        public ExternalToolVersionId() {
        }

        // Parameterized constructor
        public ExternalToolVersionId(Integer id, Integer mkItemId) {
            this.id = id;
            this.mkItemId = mkItemId;
        }

        // Getters and setters

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            ExternalToolVersionId that = (ExternalToolVersionId) o;
            return Objects.equals(id, that.id) && Objects.equals(mkItemId, that.mkItemId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, mkItemId);
        }
    }

}
