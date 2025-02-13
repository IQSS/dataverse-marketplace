package org.dataverse.marketplace.model.enums;

public enum StoredResourceStorageTypeEnum {

    DATABASE("DATABASE", 1),
    FILESYSTEM("FILESYSTEM", 2),
    CLOUD_S3("CLOUD_S3", 3),;

    private final String name;
    private final Integer id;

    private StoredResourceStorageTypeEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public static StoredResourceStorageTypeEnum fromId(Integer id) {
        for (StoredResourceStorageTypeEnum type : StoredResourceStorageTypeEnum.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }

    
}
