package org.dataverse.marketplace.service;

import org.dataverse.marketplace.model.Setting;
import org.dataverse.marketplace.repository.SettingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    private SettingRepo settingRepo;

    public boolean isRegistrationEnabled() {
        return settingRepo.findByKey("registration_enabled")
                .map(setting -> "true".equalsIgnoreCase(setting.getValue()))
                .orElse(true); // Default to true if setting doesn't exist
    }

    public void setRegistrationEnabled(boolean enabled) {
        Setting setting = settingRepo.findByKey("registration_enabled")
                .orElse(new Setting("registration_enabled", "", "Controls whether public user registration is enabled"));
        setting.setValue(enabled ? "true" : "false");
        settingRepo.save(setting);
    }

    public String getSetting(String key, String defaultValue) {
        return settingRepo.findByKey(key)
                .map(Setting::getValue)
                .orElse(defaultValue);
    }

    public void setSetting(String key, String value, String description) {
        Setting setting = settingRepo.findByKey(key)
                .orElse(new Setting(key, value, description));
        setting.setValue(value);
        if (description != null && !description.isEmpty()) {
            setting.setDescription(description);
        }
        settingRepo.save(setting);
    }
}
