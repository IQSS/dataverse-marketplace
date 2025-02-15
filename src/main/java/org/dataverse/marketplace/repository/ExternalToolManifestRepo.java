package org.dataverse.marketplace.repository;

import java.util.List;

import org.dataverse.marketplace.model.ExternalToolManifest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalToolManifestRepo extends JpaRepository<ExternalToolManifest, ExternalToolManifest.ExternalToolManifestId> {

    @Query("SELECT e FROM ExternalToolManifest e WHERE e.mkItemId = ?1 AND e.versionId = ?2")
    public List<ExternalToolManifest> findByMkItemIdAndVersionId(Integer mkItemId, Integer versionId);
}
