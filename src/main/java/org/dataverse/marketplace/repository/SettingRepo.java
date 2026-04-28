package org.dataverse.marketplace.repository;

import org.dataverse.marketplace.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepo extends JpaRepository<Setting, String> {
    Optional<Setting> findByKey(String key);
}
