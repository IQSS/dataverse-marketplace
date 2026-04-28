package org.dataverse.marketplace.service;

import org.dataverse.marketplace.model.AppSetting;
import org.dataverse.marketplace.repository.AppSettingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppSettingService {

    @Autowired
    private AppSettingRepo appSettingRepo;

    public boolean isRegistrationEnabled() {
        return appSettingRepo.findByKey("registration_enabled")
                .map(setting -> "true".equalsIgnoreCase(setting.getValue()))
                .orElse(true); // Default to true if setting doesn't exist
    }

    public void setRegistrationEnabled(boolean enabled) {
        AppSetting setting = appSettingRepo.findByKey("registration_enabled")
                .orElse(new AppSetting("registration_enabled", "", "Controls whether public user registration is enabled"));
        setting.setValue(enabled ? "true" : "false");
        appSettingRepo.save(setting);
    }

    public String getSetting(String key, String defaultValue) {
        return appSettingRepo.findByKey(key)
                .map(AppSetting::getValue)
                .orElse(defaultValue);
    }

    public void setSetting(String key, String value, String description) {
        AppSetting setting = appSettingRepo.findByKey(key)
                .orElse(new AppSetting(key, value, description));
        setting.setValue(value);
        if (description != null && !description.isEmpty()) {
            setting.setDescription(description);
        }
        appSettingRepo.save(setting);
    }
}
