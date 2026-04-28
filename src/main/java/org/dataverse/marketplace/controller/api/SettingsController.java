package org.dataverse.marketplace.controller.api;

import org.dataverse.marketplace.openapi.annotations.SettingsAPIDocs;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.security.ApplicationRoles;
import org.dataverse.marketplace.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/{key}")
    @SettingsAPIDocs.GetSetting
    public ResponseEntity<?> getSetting(@PathVariable String key) {
        String value = settingService.getSetting(key, null);

        if (value == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Setting not found",
                    String.format("No setting found with key: %s", key))
            );
        }

        Map<String, String> response = new HashMap<>();
        response.put("key", key);
        response.put("value", value);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @PutMapping("/{key}")
    @SettingsAPIDocs.SetSetting
    public ResponseEntity<?> setSetting(
            @PathVariable String key,
            @RequestParam("value") String value) {

        settingService.setSetting(key, value, null);

        return ResponseEntity.ok(new ServerMessageResponse(HttpStatus.OK,
                "Setting updated successfully",
                String.format("Setting '%s' has been updated to '%s'", key, value)));
    }
}
