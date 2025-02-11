package org.dataverse.marketplace.model;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@IdClass(ExternalToolManifest.ExternalToolManifestId.class)
@Table(name = "external_tool_manifest", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "version_id", "mk_item_id" })
})
public class ExternalToolManifest {

    @Id
    @Column(name = "version_id")
    Integer versionId;

    @Id
    @Column(name = "mk_item_id")
    private Integer mkItemId;

    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "manifest")
    private byte[] jsonData;

    @Column(name = "mime_type")
    private String mimeType;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "version_id", referencedColumnName = "id", insertable = false, updatable = false),
        @JoinColumn(name = "mk_item_id", referencedColumnName = "mk_item_id", insertable = false, updatable = false)
    })
    private ExternalToolVersion externalToolVersion;

    /* Getters and Setters */


    public Integer getVersionId() {
        return this.versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getMkItemId() {
        return this.mkItemId;
    }

    public void setMkItemId(Integer mkItemId) {
        this.mkItemId = mkItemId;
    }

    public byte[] getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(byte[] jsonData) {
        this.jsonData = jsonData;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return this.externalToolVersion;
    }

    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
    }

    @Embeddable
    public static class ExternalToolManifestId implements Serializable {

        private Integer versionId;
        private Integer mkItemId;

        // Default constructor
        public ExternalToolManifestId() {
        }

        // Parameterized constructor
        public ExternalToolManifestId(Integer versionId, Integer mkItemId) {
            this.versionId = versionId;
            this.mkItemId = mkItemId;
        }

        // Getters and setters

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
                ExternalToolManifestId that = (ExternalToolManifestId) o;
            return Objects.equals(versionId, that.versionId) && Objects.equals(mkItemId, that.mkItemId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(versionId, mkItemId);
        }
    }

}
