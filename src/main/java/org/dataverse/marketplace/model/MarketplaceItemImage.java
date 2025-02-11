package org.dataverse.marketplace.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
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

    private String alt_text;

    @Lob
    private byte[] image;

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

    public String getAlt_text() {
        return this.alt_text;
    }

    public void setAlt_text(String alt_text) {
        this.alt_text = alt_text;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    



}
