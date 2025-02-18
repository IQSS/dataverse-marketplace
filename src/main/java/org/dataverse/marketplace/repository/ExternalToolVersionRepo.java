package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.ExternalToolVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalToolVersionRepo extends JpaRepository<ExternalToolVersion, ExternalToolVersion.ExternalToolVersionId> {
    
     // @Query("SELECT MAX(e.id) FROM ExternalTool e WHERE e.itemId = ?1")
    //Integer getNextIdForItem(Integer itemId);

    @Query("SELECT MAX(e.id) FROM ExternalToolVersion e WHERE e.mkItemId = ?1")
    public Integer getNextIdForIten(Integer itemId);

    @Query("SELECT e FROM ExternalToolVersion e WHERE e.mkItemId = ?1 AND e.id = ?2")
    public ExternalToolVersion findByMkItemIdAndId(Integer itemId, Integer id);

    @Query("SELECT COUNT(e) FROM ExternalToolVersion e WHERE e.mkItemId = ?1")
    public Integer getVersionCount(Integer itemId);

}
