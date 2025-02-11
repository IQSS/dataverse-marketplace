package org.dataverse.marketplace.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@IdClass(ExternalToolVersion.ExternalToolVersionId.class)
@Table(name = "external_tool_version", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id", "mk_item_id" })
})
public class ExternalToolVersion {

    @Id
    Integer id;

    @Id
    @Column(name = "mk_item_id")
    private Integer mkItemId;

    @ManyToOne
    @JoinColumn(name = "mk_item_id", insertable = false, updatable = false)
    private ExternalTool externalTool;

    @Column(name = "release_note")
    private String releaseNote;

    @Column(name = "item_version")
    private String version;

    @Column(name = "dv_min_version")
    private String dataverseMinVersion;

    @OneToMany(mappedBy = "externalToolVersion")
    private List<ExternalToolManifest> manifests;
    

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
