package org.dataverse.marketplace.repository;

import java.util.List;

import org.dataverse.marketplace.model.ExternalToolManifest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalToolManifestRepo extends JpaRepository<ExternalToolManifest,Long> {

    public List<ExternalToolManifest> findByExternalToolVersionId(Long versionId);


    }
