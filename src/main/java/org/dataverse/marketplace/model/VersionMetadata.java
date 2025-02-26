package org.dataverse.marketplace.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class VersionMetadata implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "version_metadata_id_seq")    
    @SequenceGenerator(
        name = "version_metadata_id_seq", 
        sequenceName = "version_metadata_id_seq", 
        allocationSize = 1)
    private Integer id;

    @Column(name = "release_note")
    private String releaseNote;

    @Column(name = "item_version")
    private String version;

    @Column(name = "dv_min_version")
    private String dataverseMinVersion;

    /* Getters and Setters */

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
