package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.StoredResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredResourceRepo extends JpaRepository<StoredResource, Integer> {

}
