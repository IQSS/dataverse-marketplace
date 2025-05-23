package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.MarketplaceItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceItemImageRepo extends JpaRepository<MarketplaceItemImage, Long> {
}
