package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.MarketplaceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceItemRepo extends JpaRepository<MarketplaceItem, Integer>{

}
