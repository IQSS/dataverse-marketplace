package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.ExternalToolVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalToolVersionRepo extends JpaRepository<ExternalToolVersion, Long> {

    public Long countByExternalToolId(Long ownerId);

}
