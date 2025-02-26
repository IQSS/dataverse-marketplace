package org.dataverse.marketplace.model;


import jakarta.persistence.*;

@Entity
@Table(name = "stored_resource")
public class StoredResource {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_storage_id_seq")    
    @SequenceGenerator(
        name = "resource_storage_id_seq", 
        sequenceName = "resource_storage_id_seq", 
        allocationSize = 1)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "storage_type_id")
    private StoredResourceStorageType type;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "mime_type")
    String mimeType;

    @Column(name = "file_size")
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
