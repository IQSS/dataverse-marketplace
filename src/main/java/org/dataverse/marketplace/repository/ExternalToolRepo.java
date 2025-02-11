package org.dataverse.marketplace.repository;


import org.dataverse.marketplace.model.ExternalTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExternalToolRepo extends JpaRepository<ExternalTool, Integer> {
    
   // @Query("SELECT MAX(e.id) FROM ExternalTool e WHERE e.itemId = ?1")
    //Integer getNextIdForItem(Integer itemId);

}
