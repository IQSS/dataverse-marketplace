package org.dataverse.marketplace.service;

import java.io.IOException;
import java.nio.file.*;

import org.dataverse.marketplace.model.DatabaseResourceStorage;
import org.dataverse.marketplace.model.StoredResource;
import org.dataverse.marketplace.model.StoredResourceStorageType;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.repository.DatabaseResourceStorageRepo;
import org.dataverse.marketplace.repository.StoredResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service    
public class ResourceStorageService {

    @Autowired
    StoredResourceRepo storedResourceRepo;

    @Autowired
    DatabaseResourceStorageRepo databaseResourceStorageRepo;

    @Value("${dataverse.mkt.storagePath}")
    private String diskStoragePath;

    public StoredResource getStoredResourceById(Long resourceId) {
        return storedResourceRepo.findById(resourceId).orElse(null);
    }

    public Resource getResourceBytes(StoredResource storedResource) throws IOException {
        
        if(storedResource.getType().getId() == StoredResourceStorageTypeEnum.DATABASE.getId()) {
            DatabaseResourceStorage dbResourceStorage = databaseResourceStorageRepo.findById(storedResource.getId()).orElse(null);
            byte[] data = dbResourceStorage.getResourceData(); 
            return new ByteArrayResource(data);
        } else if (storedResource.getType().getId() == StoredResourceStorageTypeEnum.FILESYSTEM.getId()) {
            Path filePath = Paths.get(diskStoragePath, storedResource.getId().toString());
            return new ByteArrayResource(Files.readAllBytes(filePath));
        }

        throw new NoSuchFileException("File not found for resource id: " + storedResource.getId());
    }    

    public Long storeResource(MultipartFile multipartFile, StoredResourceStorageTypeEnum storageTypeEnum) throws IOException {

        StoredResourceStorageType storageType = new StoredResourceStorageType();
        storageType.setId(storageTypeEnum.getId());
        storageType.setTypeName(storageTypeEnum.getName());

        StoredResource storedResource = new StoredResource();
        storedResource.setFileName(multipartFile.getOriginalFilename());
        storedResource.setMimeType(multipartFile.getContentType());
        storedResource.setFileSize(multipartFile.getSize());
        storedResource.setType(storageType);
        storedResourceRepo.save(storedResource);

        if(storageTypeEnum == StoredResourceStorageTypeEnum.DATABASE) {
            DatabaseResourceStorage dbResourceStorage = new DatabaseResourceStorage();
            dbResourceStorage.setStoredResourceId(storedResource.getId());
            dbResourceStorage.setResourceData(multipartFile.getBytes());
            databaseResourceStorageRepo.save(dbResourceStorage);
            return dbResourceStorage.getStoredResourceId();
        } else if (storageTypeEnum == StoredResourceStorageTypeEnum.FILESYSTEM) {
            Path filePath = Paths.get(diskStoragePath, storedResource.getId().toString());
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return storedResource.getId();
        }

        throw new IllegalArgumentException("Unsupported storage type: " + storageType);
    }

    /**
     * This method deletes the content of a stored resource depending on the storage type.
     */
    public void deleteResourceContent(Long resourceId) throws IOException {
        StoredResource storedResource = storedResourceRepo.findById(resourceId).orElse(null);
        if(storedResource == null) {
            throw new NoSuchFileException("File not found for resource id: " + resourceId);
        }

        if(storedResource.getType().getId() == StoredResourceStorageTypeEnum.DATABASE.getId()) {
            databaseResourceStorageRepo.deleteById(resourceId);
        } else if (storedResource.getType().getId() == StoredResourceStorageTypeEnum.FILESYSTEM.getId()) {
            Path filePath = Paths.get(diskStoragePath, storedResource.getId().toString());
            Files.delete(filePath);
        }
    }

}
