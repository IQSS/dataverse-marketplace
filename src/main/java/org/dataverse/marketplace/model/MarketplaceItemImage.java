package org.dataverse.marketplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "marketplace_item_image")
public class MarketplaceItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_stored_resource_id")
    private StoredResource storedResource;

    @ManyToOne
    @JoinColumn(name = "mkt_item_id")
    private MarketplaceItem marketplaceItem;

    /* Getters & Setters */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarketplaceItem getMarketplaceItem() {
        return this.marketplaceItem;
    }

    public void setMarketplaceItem(MarketplaceItem marketplaceItem) {
        this.marketplaceItem = marketplaceItem;
    }

    public StoredResource getStoredResource() {
        return this.storedResource;
    }

    public void setStoredResource(StoredResource storedResource) {
        this.storedResource = storedResource;
    }
    
}
