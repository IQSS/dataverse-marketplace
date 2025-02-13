package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.DatabaseResourceStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseResourceStorageRepo extends JpaRepository<DatabaseResourceStorage, Long> { 

}
