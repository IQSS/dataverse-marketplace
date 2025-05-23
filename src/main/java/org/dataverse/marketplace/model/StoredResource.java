package org.dataverse.marketplace.model;


import jakarta.persistence.*;

@Entity
public class StoredResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "storage_type_id", nullable = false)
    private StoredResourceStorageType type;

    String fileName;

    String mimeType;

    Long fileSize;

    /* Getters and Setters */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoredResourceStorageType getType() {
        return this.type;
    }

    public void setType(StoredResourceStorageType type) {
        this.type = type;
    }
   
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

}
