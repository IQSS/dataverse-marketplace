package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.ExternalToolManifest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalToolManifestRepo extends JpaRepository<ExternalToolManifest, ExternalToolManifest.ExternalToolManifestId> {

}
