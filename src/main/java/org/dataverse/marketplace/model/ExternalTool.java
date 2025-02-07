package org.dataverse.marketplace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "external_tool")
public class ExternalTool {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ext_tool_id_seq")    
    @SequenceGenerator(
        name = "ext_tool_id_seq", 
        sequenceName = "ext_tool_id_seq", 
        allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "mk_item_id")
    MarketplaceItem marketplaceItem;

    @Column(name = "release_note")
    private String releaseNote;

    @Column(name = "item_version")
    private String version;

    @Column(name = "dataverse_min_version")
    private String dataverseMinVersion;

    @Column(name = "json_data")
    private byte[] jsonData;

    /* Getters & Setters */

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MarketplaceItem getMarketplaceItem() {
        return this.marketplaceItem;
    }

    public void setMarketplaceItem(MarketplaceItem marketplaceItem) {
        this.marketplaceItem = marketplaceItem;
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
