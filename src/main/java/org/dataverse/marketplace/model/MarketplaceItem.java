package org.dataverse.marketplace.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MarketplaceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "marketplaceItem")
    private List<MarketplaceItemImage> images;

    @ManyToMany
    @JoinTable(
        name = "item_tags",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<ItemTag> tags;

    @ManyToOne (optional = false)
    @JoinColumn(name = "owner_id", nullable = false) 
    User owner;    

    /* Getters & Setters */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public Set<ItemTag> getTags() {
        return this.tags;
    }

    public void setTags(Set<ItemTag> tags) {
        this.tags = tags;
    }

    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }    
    
}
