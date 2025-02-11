package org.dataverse.marketplace.model;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "external_tool_version")
public class ExternalToolVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ext_tool_version_id_seq")    
    @SequenceGenerator(
        name = "ext_tool_version_id_seq", 
        sequenceName = "ext_tool_version_id_seq", 
        allocationSize = 1)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "mk_item_id")
    ExternalTool externalTool;

    @Column(name = "release_note")
    private String releaseNote;

    @Column(name = "item_version")
    private String version;

    @Column(name = "dv_min_version")
    private String dataverseMinVersion;

    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "json_data")
    private byte[] jsonData;




    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public byte[] getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(byte[] jsonData) {
        this.jsonData = jsonData;
    }

   

}
