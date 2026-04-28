package org.dataverse.marketplace.repository;


import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.enums.ItemStatus;
import org.dataverse.marketplace.model.enums.Scope;
import org.dataverse.marketplace.model.enums.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ExternalToolRepo extends JpaRepository<ExternalTool, Long> {

    public List<ExternalTool> findByOwnerId(Long ownerId);

    @Query("SELECT DISTINCT t FROM ExternalTool t " +
           "JOIN t.externalToolVersions v " +
           "LEFT JOIN v.externalToolTypes tt " +
           "WHERE (:scope IS NULL OR v.scope = :scope) " +
           "AND (:type IS NULL OR tt.type = :type)")
    List<ExternalTool> findByFilters(@Param("scope") Scope scope, @Param("type") ToolType type);

    @Query("SELECT DISTINCT t FROM ExternalTool t " +
           "JOIN t.externalToolVersions v " +
           "LEFT JOIN v.externalToolTypes tt " +
           "WHERE (:isAdmin = true OR t.status = org.dataverse.marketplace.model.enums.ItemStatus.PUBLIC OR (:userId IS NOT NULL AND t.owner.id = :userId)) " +
           "AND (:scope IS NULL OR v.scope = :scope) " +
           "AND (:type IS NULL OR tt.type = :type) " +
           "AND (:status IS NULL OR t.status = :status)")
    List<ExternalTool> findByFiltersAndVisibility(
        @Param("isAdmin") boolean isAdmin,
        @Param("userId") Long userId,
        @Param("scope") Scope scope,
        @Param("type") ToolType type,
        @Param("status") ItemStatus status
    );

}
