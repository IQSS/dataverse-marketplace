package org.dataverse.marketplace.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ToolType {
    PREVIEW,
    EXPLORE,
    CONFIGURE,
    QUERY;

    @JsonValue
    public String toLower() {
        return name().toLowerCase();
    }
}
