package org.dataverse.marketplace.service;

import java.io.IOException;

import org.dataverse.marketplace.model.DatabaseResourceStorage;
import org.dataverse.marketplace.model.StoredResource;
import org.dataverse.marketplace.model.StoredResourceStorageType;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.repository.DatabaseResourceStorageRepo;
import org.dataverse.marketplace.repository.StoredResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service    
public class ResourceStorageService {

    @Autowired
    StoredResourceRepo storedResourceRepo;

    @Autowired
    DatabaseResourceStorageRepo databaseResourceStorageRepo;

    public byte[] getResourceById(Long resourceId) {
        return null;
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
        }

        throw new IllegalArgumentException("Unsupported storage type: " + storageType);
    }

}
