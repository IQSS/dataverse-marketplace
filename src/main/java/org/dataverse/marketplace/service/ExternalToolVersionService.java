package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.StoredResource;
import org.dataverse.marketplace.model.VersionMetadata;
import org.dataverse.marketplace.model.ExternalToolManifest.ExternalToolManifestId;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.repository.ExternalToolManifestRepo;
import org.dataverse.marketplace.repository.ExternalToolVersionRepo;
import org.dataverse.marketplace.repository.VersionMetadataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
        
        Integer externalToolNextVersion = externalToolVersionRepo.getNextIdForItemVersion(toolId);
        externalToolNextVersion = externalToolNextVersion == null ? 1 : externalToolNextVersion + 1;

        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setId(externalToolNextVersion);
        newVersion.setMkItemId(toolId);
        newVersion.setVersionMetadata(versionMetadata);
        externalToolVersionRepo.save(newVersion);

        newVersion.setManifests(addVersionManifests(newVersion, externalToolVersion.getJsonData()));

        return newVersion;
    }

    @Transactional
    public List<ExternalToolManifest> addVersionManifests(ExternalToolVersion externalToolVersion, List<MultipartFile> manifests) throws IOException {

        List<ExternalToolManifest> newManifests = new ArrayList<ExternalToolManifest>();

        for (MultipartFile manifest : manifests) {

            StoredResource storedResource = resourceStorageService
                                        .storeResource(manifest, 
                                        StoredResourceStorageTypeEnum.FILESYSTEM);

            

            Integer externalToolManifestId = externalToolManifestRepo.getNextIdForManifest(
                    externalToolVersion.getMkItemId(), externalToolVersion.getId());

            externalToolManifestId = externalToolManifestId == null ? 1 : externalToolManifestId + 1;

            ExternalToolManifest newManifest = new ExternalToolManifest();
            newManifest.setManifestId(externalToolManifestId);
            newManifest.setMkItemId(externalToolVersion.getMkItemId());
            newManifest.setVersionId(externalToolVersion.getId());
            newManifest.setManifestStoredResourceId(storedResource.getId());
            newManifest.setStoredResource(storedResource);
            
            String jsonString = new String(manifest.getBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String contentType = jsonNode.get("contentType").asText();
            newManifest.setMimeType(contentType);
            externalToolManifestRepo.save(newManifest);

            



            newManifests.add(newManifest);
        }
        return newManifests;
    }
    

    public void deleteToolManifest(Integer toolId,
            Integer versionId,
            Integer manifestId) throws IOException {

        ExternalToolManifestId externalToolManifestId = new ExternalToolManifestId();
        externalToolManifestId.setMkItemId(toolId);
        externalToolManifestId.setVersionId(versionId);
        externalToolManifestId.setManifestId(manifestId); 

        ExternalToolManifest manifest = externalToolManifestRepo.findById(externalToolManifestId).get();
        resourceStorageService.deleteResourceContent(manifest.getManifestStoredResourceId());
        externalToolManifestRepo.delete(manifest);
    }
}
