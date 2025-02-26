package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.model.enums.StoredResourceStorageTypeEnum;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    private ExternalToolVersionService externalToolVersionService;

    @Autowired
    private ResourceStorageService resourceStorageService;

    @Autowired
    private MarketplaceItemImageRepo marketplaceItemImageRepo;
    
    @Cacheable(value = "externalTools", key = "#root.methodName")
    public List<ExternalToolDTO> getAllTools() {
        List<ExternalTool> tools = externalToolRepo.findAll();
        ArrayList<ExternalToolDTO> toolDTOs = new ArrayList<>();
        for (ExternalTool tool : tools) {
            toolDTOs.add(new ExternalToolDTO(tool));
        }
        return toolDTOs;
    }

    public ExternalTool getToolById(Integer toolId) {
        return externalToolRepo.findById(toolId).orElse(null);
    }

    public ExternalToolVersion getToolVersionById(Integer toolId, Integer versionId) {
        return externalToolVersionRepo.findByMkItemIdAndId(toolId, versionId);
    }

    public List<ExternalToolManifest> getToolManifests(Integer toolId, Integer versionId) {
        return externalToolManifestRepo.findByMkItemIdAndVersionId(toolId, versionId);
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    @Transactional
    public ExternalToolDTO addTool(AddToolRequest addToolRequest) throws IOException {

        ExternalTool newTool = new ExternalTool();
        newTool.setName(addToolRequest.getName());
        newTool.setDescription(addToolRequest.getDescription());
        externalToolRepo.save(newTool);

        AddVersionRequest addVersionRequest = new AddVersionRequest();
        addVersionRequest.setVersion(addToolRequest.getVersion());
        addVersionRequest.setReleaseNote(addToolRequest.getReleaseNote());
        addVersionRequest.setDvMinVersion(addToolRequest.getDvMinVersion());
        addVersionRequest.setJsonData(addToolRequest.getJsonData());
        
        List<ExternalToolVersion> versions = new ArrayList<ExternalToolVersion>();
        ExternalToolVersion newVersion =
            externalToolVersionService.addToolVersion(addVersionRequest, newTool.getId());
        versions.add(newVersion);
        newTool.setExternalToolVersions(versions);
        if(addToolRequest.getItemImages() != null){
            newTool.setImages(addItemImages(newTool, addToolRequest.getItemImages()));
        }
        

        return new ExternalToolDTO(newTool);
    }

    public ExternalTool updateTool(ExternalTool tool, UpdateToolRequest updateToolRequest) {
       
        tool.setName(updateToolRequest.getName());
        tool.setDescription(updateToolRequest.getDescription());
        externalToolRepo.save(tool);
        return tool;
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    @Transactional
    public List<MarketplaceItemImage> addItemImages(MarketplaceItem item, List<MultipartFile> images) throws IOException {

        List<MarketplaceItemImage> newImages = new ArrayList<MarketplaceItemImage>();

        for (MultipartFile image : images) {

            Long storedResourceId = resourceStorageService
                                        .storeResource(image, 
                                        StoredResourceStorageTypeEnum.FILESYSTEM);

            MarketplaceItemImage newImage = new MarketplaceItemImage();
            
            newImage.setMarketplaceItem(item);
            newImage.setImageStoredResourceId(storedResourceId);
            marketplaceItemImageRepo.save(newImage);
            newImages.add(newImage);
        }

        
        return newImages;
    }

    public MarketplaceItemImage getItemImage(Integer imageId, Integer marketplaceItemId) {
        return marketplaceItemImageRepo.findByMarketplaceItemId(imageId, marketplaceItemId);
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    public void deleteToolImage(MarketplaceItemImage image) {
        marketplaceItemImageRepo.delete(image);
    }

}
