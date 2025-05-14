package org.dataverse.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.dataverse.marketplace.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
