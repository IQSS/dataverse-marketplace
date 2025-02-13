package org.dataverse.marketplace.service;

import org.dataverse.marketplace.model.DatabaseResourceStorage;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.repository.StoredResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    
public class ResourceStorageService {

    @Autowired
    StoredResourceRepo storedResourceRepo;

    @Autowired
    databaseResourceStorageRepo databaseResourceStorageRepo;

    public byte[] getResourceById(Long resourceId) {
        return null;
    }

    public Long storeResource(byte[] resourceData, StoredResourceStorageTypeEnum storageType) {
        if(storageType == StoredResourceStorageTypeEnum.DATABASE) {
            DatabaseResourceStorage dbResourceStorage = new DatabaseResourceStorage();
            dbResourceStorage.setResourceData(resourceData);

            return storedResourceRepo.save(dbResourceStorage).getId();
        }
        return null;
    }

}
