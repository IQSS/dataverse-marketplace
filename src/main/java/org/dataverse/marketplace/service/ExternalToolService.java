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

    public List<ExternalToolDTO> getAllToolsByOwnerId(Long ownerId) {
        List<ExternalTool> tools = externalToolRepo.findByOwnerId(ownerId);
        ArrayList<ExternalToolDTO> toolDTOs = new ArrayList<>();
        for (ExternalTool tool : tools) {
            toolDTOs.add(new ExternalToolDTO(tool));
        }
        return toolDTOs;
    }    

    public ExternalTool getToolById(Long toolId) {
        return externalToolRepo.findById(toolId).orElse(null);
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    @Transactional
    public ExternalToolDTO addTool(AddToolRequest addToolRequest, User owner) throws IOException {

        ExternalTool newTool = new ExternalTool();
        newTool.setOwner(owner);
        newTool.setName(addToolRequest.getName());
        newTool.setDescription(addToolRequest.getDescription());
        externalToolRepo.save(newTool);

        AddVersionRequest addVersionRequest = new AddVersionRequest();
        addVersionRequest.setVersion(addToolRequest.getVersion());
        addVersionRequest.setReleaseNote(addToolRequest.getReleaseNote());
        addVersionRequest.setDvMinVersion(addToolRequest.getDvMinVersion());
        addVersionRequest.setManifest(addToolRequest.getManifest());
        
        List<ExternalToolVersion> versions = new ArrayList<ExternalToolVersion>();
        ExternalToolVersion newVersion =
            externalToolVersionService.addToolVersion(addVersionRequest, newTool);
        versions.add(newVersion);
        newTool.setExternalToolVersions(versions);
        if(addToolRequest.getItemImages() != null){
            newTool.setImages(addItemImages(newTool, addToolRequest.getItemImages()));
        }
        

        return new ExternalToolDTO(newTool);
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    public ExternalTool updateTool(ExternalTool tool, UpdateToolRequest updateToolRequest) {
       
        tool.setName(updateToolRequest.getName());
        tool.setDescription(updateToolRequest.getDescription());
        externalToolRepo.save(tool);
        return tool;
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    public void deleteTool(ExternalTool tool) {
        externalToolRepo.delete(tool);
    }    

    /**
     * image related methods
     */

    @CacheEvict(value = "externalTools", allEntries = true)
    @Transactional
    public List<MarketplaceItemImage> addItemImages(MarketplaceItem item, List<MultipartFile> images) throws IOException {

        List<MarketplaceItemImage> newImages = new ArrayList<MarketplaceItemImage>();

        for (MultipartFile image : images) {

            StoredResource storedResource = resourceStorageService
                                        .storeResource(image, 
                                        StoredResourceStorageTypeEnum.FILESYSTEM);

            MarketplaceItemImage newImage = new MarketplaceItemImage();
            newImage.setMarketplaceItem(item);
            newImage.setStoredResource(storedResource);          
            marketplaceItemImageRepo.save(newImage);
            newImages.add(newImage);
        }

        
        return newImages;
    }

    public MarketplaceItemImage getItemImage(Long imageId) {
        return marketplaceItemImageRepo.findById(imageId).orElse(null);
    }

    @CacheEvict(value = "externalTools", allEntries = true)
    public void deleteToolImage(MarketplaceItemImage image) {
        marketplaceItemImageRepo.delete(image);
    }
    

}
