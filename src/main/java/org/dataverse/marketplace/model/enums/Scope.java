package org.dataverse.marketplace.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Scope {
    DATASET,
    FILE;

    @JsonValue
    public String toLower() {
        return name().toLowerCase();
    }
}