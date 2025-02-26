package org.dataverse.marketplace.repository;


import org.dataverse.marketplace.model.ExternalTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ExternalToolRepo extends JpaRepository<ExternalTool, Integer> {

}
