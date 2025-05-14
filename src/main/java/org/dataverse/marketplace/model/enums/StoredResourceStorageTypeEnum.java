package org.dataverse.marketplace.model.enums;

public enum StoredResourceStorageTypeEnum {

    DATABASE("DATABASE", Long.valueOf(1)),
    FILESYSTEM("FILESYSTEM", Long.valueOf(2)),
    CLOUD_S3("CLOUD_S3", Long.valueOf(3)),;

    private final String name;
    private final Long id;

    private StoredResourceStorageTypeEnum(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static StoredResourceStorageTypeEnum fromId(Long id) {
        for (StoredResourceStorageTypeEnum type : StoredResourceStorageTypeEnum.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }

    
}
