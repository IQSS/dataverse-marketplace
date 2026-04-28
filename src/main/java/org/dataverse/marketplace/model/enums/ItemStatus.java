package org.dataverse.marketplace.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemStatus {
    DRAFT("draft"),
    PUBLIC("public");

    private final String value;

    ItemStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
