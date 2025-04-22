package org.dataverse.marketplace.model;

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
@Table(name = "marketplace_item_image")
public class MarketplaceItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mkt_item_image_id_seq")    
    @SequenceGenerator(
        name = "mkt_item_image_id_seq", 
        sequenceName = "mkt_item_image_id_seq", 
        allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mkt_item_id")
    private MarketplaceItem marketplaceItem;

    @Column(name = "image_stored_resource_id")
    private Long imageStoredResourceId;

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

    public Long getImageStoredResourceId() {
        return this.imageStoredResourceId;
    }

    public void setImageStoredResourceId(Long imageStoredResourceId) {
        this.imageStoredResourceId = imageStoredResourceId;
    }
    
}
