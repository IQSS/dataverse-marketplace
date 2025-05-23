package org.dataverse.marketplace.model;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DatabaseResourceStorage {

    @Id
    private Long storedResourceId;
    

    @JdbcType(VarbinaryJdbcType.class)
    private byte[] resourceData;

    /* Getters and Setters */

    public Long getStoredResourceId() {
        return this.storedResourceId;
    }

    public void setStoredResourceId(Long storedResourceId) {
        this.storedResourceId = storedResourceId;
    }

    public byte[] getResourceData() {
        return this.resourceData;
    }

    public void setResourceData(byte[] resourceData) {
        this.resourceData = resourceData;
    }

}
