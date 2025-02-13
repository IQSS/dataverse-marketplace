package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.*;
import jakarta.transaction.Transactional;

@Service
public class ExternalToolService {

    @Autowired
    private ExternalToolRepo externalToolRepo;

    @Autowired
    private ExternalToolVersionRepo externalToolVersionRepo;

    @Autowired
    private ExternalToolManifestRepo externalToolManifestRepo;

    @Autowired
    private VersionMetadataRepo versionMetadataRepo;

    @Autowired
    private ResourceStorageService resourceStorageService;
    
    public List<ExternalTool> getAllTools() {
        return externalToolRepo.findAll();
    }

    @Transactional
    public ExternalToolDTO addTool(AddToolRequest externalTool) throws IOException {

        ExternalTool newTool = new ExternalTool();
        newTool.setName(externalTool.getName());
        newTool.setDescription(externalTool.getDescription());
        externalToolRepo.save(newTool);

        Integer externalToolNextVersion = externalToolVersionRepo.getNextIdForIten(newTool.getId());
        externalToolNextVersion = externalToolNextVersion == null ? 1 : externalToolNextVersion + 1;
        
        VersionMetadata versionMetadata = new VersionMetadata();
        versionMetadata.setVersion(externalTool.getVersion());
        versionMetadata.setReleaseNote(externalTool.getReleaseNote());
        versionMetadata.setDataverseMinVersion(externalTool.getDvMinVersion());
        versionMetadataRepo.save(versionMetadata);

        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setId(externalToolNextVersion);
        newVersion.setMkItemId(newTool.getId());
        newVersion.setVersionMetadata(versionMetadata);
        externalToolVersionRepo.save(newVersion);

        List<ExternalToolVersion> versions = new ArrayList<ExternalToolVersion>();
        versions.add(newVersion);
        newTool.setExternalToolVersions(versions);

        List<ExternalToolManifest> manifests = new ArrayList<ExternalToolManifest>();
        newVersion.setManifests(manifests);

        for (MultipartFile manifest : externalTool.getJsonData()) {
            
            Long storedResourceId = resourceStorageService
                                        .storeResource(manifest, 
                                        StoredResourceStorageTypeEnum.FILESYSTEM);

            ExternalToolManifest newManifest = new ExternalToolManifest();
            newManifest.setMkItemId(newTool.getId());
            newManifest.setVersionId(newVersion.getId());
            newManifest.setManifestStoredResourceId(storedResourceId);
            
            String jsonString = new String(manifest.getBytes());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String contentType = jsonNode.get("contentType").asText();
            newManifest.setMimeType(contentType);
            manifests.add(newManifest);
            
            externalToolManifestRepo.save(newManifest);
        }

        return new ExternalToolDTO(newTool);
    }

}
