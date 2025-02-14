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

    @Autowired
    private MarketplaceItemImageRepo marketplaceItemImageRepo;
    
    public List<ExternalTool> getAllTools() {
        return externalToolRepo.findAll();
    }

    public ExternalTool getToolById(Integer toolId) {
        return externalToolRepo.findById(toolId).orElse(null);
    }

    @Transactional
    public ExternalToolDTO addTool(AddToolRequest addToolRequest) throws IOException {

        ExternalTool newTool = new ExternalTool();
        newTool.setName(addToolRequest.getName());
        newTool.setDescription(addToolRequest.getDescription());
        externalToolRepo.save(newTool);

        Integer externalToolNextVersion = externalToolVersionRepo.getNextIdForIten(newTool.getId());
        externalToolNextVersion = externalToolNextVersion == null ? 1 : externalToolNextVersion + 1;
        
        VersionMetadata versionMetadata = new VersionMetadata();
        versionMetadata.setVersion(addToolRequest.getVersion());
        versionMetadata.setReleaseNote(addToolRequest.getReleaseNote());
        versionMetadata.setDataverseMinVersion(addToolRequest.getDvMinVersion());
        versionMetadataRepo.save(versionMetadata);

        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setId(externalToolNextVersion);
        newVersion.setMkItemId(newTool.getId());
        newVersion.setVersionMetadata(versionMetadata);
        externalToolVersionRepo.save(newVersion);

        List<ExternalToolVersion> versions = new ArrayList<ExternalToolVersion>();
        versions.add(newVersion);
        newTool.setExternalToolVersions(versions);

        newVersion.setManifests(new ArrayList<ExternalToolManifest>());

        for (MultipartFile manifest : addToolRequest.getJsonData()) {
            
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
            externalToolManifestRepo.save(newManifest);

            newVersion.getManifests().add(newManifest);
        }

        newTool.setImages(new ArrayList<MarketplaceItemImage>());

        for (MultipartFile image : addToolRequest.getItemImages()) {

            Long storedResourceId = resourceStorageService
                                        .storeResource(image, 
                                        StoredResourceStorageTypeEnum.FILESYSTEM);

            MarketplaceItemImage newImage = new MarketplaceItemImage();
            newImage.setMarketplaceItem(newTool);
            newImage.setManifestStoredResourceId(storedResourceId);
            newImage.setAltText(image.getOriginalFilename());

            marketplaceItemImageRepo.save(newImage);
            newTool.getImages().add(newImage);
        }

        return new ExternalToolDTO(newTool);
    }

    

}
