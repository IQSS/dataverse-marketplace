package org.dataverse.marketplace.model;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "database_resource_storage")
public class DatabaseResourceStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "db_storage_id_seq")    
    @SequenceGenerator(
        name = "db_storage_id_seq", 
        sequenceName = "db_storage_id_seq", 
        allocationSize = 1)
    private Long storedResourceId;

    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "resource_data")
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
