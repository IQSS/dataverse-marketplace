package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.ArrayList;

import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.VersionMetadata;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.repository.ExternalToolManifestRepo;
import org.dataverse.marketplace.repository.ExternalToolVersionRepo;
import org.dataverse.marketplace.repository.VersionMetadataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class ExternalToolVersionService {

    @Autowired
    private ExternalToolVersionRepo externalToolVersionRepo;

    @Autowired
    private ResourceStorageService resourceStorageService;

    @Autowired
    private VersionMetadataRepo versionMetadataRepo;

    @Autowired
    private ExternalToolManifestRepo externalToolManifestRepo;

    public ExternalToolVersion updateToolVersion(ExternalToolVersion externalToolVersion) {
        return externalToolVersionRepo.save(externalToolVersion);
    }

    public Integer getVersionCount(Integer toolId) {
        return externalToolVersionRepo.getVersionCount(toolId);
    }

    @Transactional
    public void deleteToolVersion(ExternalToolVersion externalToolVersion) throws IOException{
        for(ExternalToolManifest manifest : externalToolVersion.getManifests()) {
            resourceStorageService.deleteResourceContent(manifest.getManifestStoredResourceId());
        }
        externalToolVersionRepo.delete(externalToolVersion);        
    }

    @Transactional
    public ExternalToolVersion addToolVersion(AddVersionRequest externalToolVersion, Integer toolId) throws IOException {

        VersionMetadata versionMetadata = new VersionMetadata();
        versionMetadata.setVersion(externalToolVersion.getVersion());
        versionMetadata.setReleaseNote(externalToolVersion.getReleaseNote());
        versionMetadata.setDataverseMinVersion(externalToolVersion.getDvMinVersion());
        versionMetadataRepo.save(versionMetadata);
        
        Integer externalToolNextVersion = externalToolVersionRepo.getNextIdForIten(toolId);
        externalToolNextVersion = externalToolNextVersion == null ? 1 : externalToolNextVersion + 1;

        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setId(externalToolNextVersion);
        newVersion.setMkItemId(toolId);
        newVersion.setVersionMetadata(versionMetadata);
        externalToolVersionRepo.save(newVersion);

        newVersion.setManifests(new ArrayList<ExternalToolManifest>());

        for(MultipartFile manifest : externalToolVersion.getJsonData()) {
            
            Long storedResourceId = resourceStorageService
                                        .storeResource(manifest, 
                                        StoredResourceStorageTypeEnum.FILESYSTEM);

            ExternalToolManifest newManifest = new ExternalToolManifest();
            newManifest.setMkItemId(toolId);
            newManifest.setVersionId(newVersion.getId());
            newManifest.setManifestStoredResourceId(storedResourceId);
            
            String jsonString = new String(manifest.getBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String contentType = jsonNode.get("contentType").asText();
            newManifest.setMimeType(contentType);
            externalToolManifestRepo.save(newManifest);
            newVersion.getManifests().add(newManifest);
        }

        return newVersion;
    }   
}
