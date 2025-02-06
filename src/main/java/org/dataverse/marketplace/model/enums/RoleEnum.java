package org.dataverse.marketplace.model.enums;

public enum RoleEnum {

    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
