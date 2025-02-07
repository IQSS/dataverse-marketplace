package org.dataverse.marketplace.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "marketplace_item")
public class MarketplaceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mkt_item_id_seq")    
    @SequenceGenerator(
        name = "mkt_item_id_seq", 
        sequenceName = "mkt_item_id_seq", 
        allocationSize = 1)
    private Integer id;

    private String name;

    private String description;

    @OneToOne(mappedBy = "marketplaceItem")
    private ExternalTool externalTool;

    @OneToMany(mappedBy = "marketplaceItem")
    private List<MarketplaceItemImage> images;

    /* Getters & Setters */

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExternalTool getExternalTool() {
        return this.externalTool;
    }

    public void setExternalTool(ExternalTool externalTool) {
        this.externalTool = externalTool;
    }

    public List<MarketplaceItemImage> getImages() {
        return this.images;
    }

    public void setImages(List<MarketplaceItemImage> images) {
        this.images = images;
    }

    

}
