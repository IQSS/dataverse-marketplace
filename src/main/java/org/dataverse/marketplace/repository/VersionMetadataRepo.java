package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.VersionMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionMetadataRepo extends JpaRepository<VersionMetadata, Integer> {

}
