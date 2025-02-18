package org.dataverse.marketplace.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "image_stored_resource_id")
    private Long imageStoredResourceId;

    @ManyToMany
    @JoinTable(
        name = "item_tag",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<ItemTag> tags;

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

    public String getAltText() {
        return this.altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Long getImageStoredResourceId() {
        return this.imageStoredResourceId;
    }

    public void setImageStoredResourceId(Long imageStoredResourceId) {
        this.imageStoredResourceId = imageStoredResourceId;
    }
    
    public Set<ItemTag> getTags() {
        return this.tags;
    }

    public void setTags(Set<ItemTag> tags) {
        this.tags = tags;
    }

}
