package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.MarketplaceItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceItemImageRepo extends JpaRepository<MarketplaceItemImage, Integer> {

    @Query("SELECT i FROM MarketplaceItemImage i WHERE i.imageStoredResourceId = ?1 AND i.marketplaceItem.id = ?2")  
    public MarketplaceItemImage findByMarketplaceItemId(Integer imageId, Integer marketplaceItemId);
}
