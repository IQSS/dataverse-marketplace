package org.dataverse.marketplace.model;

import java.util.List;

import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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

    public List<MarketplaceItemImage> getImages() {
        return this.images;
    }

    public void setImages(List<MarketplaceItemImage> images) {
        this.images = images;
    }

    

}
