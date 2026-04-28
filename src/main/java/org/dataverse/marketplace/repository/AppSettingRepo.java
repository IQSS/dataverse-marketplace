package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppSettingRepo extends JpaRepository<AppSetting, String> {
    Optional<AppSetting> findByKey(String key);
}
