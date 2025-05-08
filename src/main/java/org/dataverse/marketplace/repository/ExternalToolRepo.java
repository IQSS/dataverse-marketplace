package org.dataverse.marketplace.repository;


import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExternalToolRepo extends JpaRepository<ExternalTool, Integer> {

    public List<ExternalTool> findByOwnerId(Integer ownerId);
    
}
