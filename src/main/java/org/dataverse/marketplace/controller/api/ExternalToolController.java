package org.dataverse.marketplace.controller.api;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.annotations.ExternalToolsAPIDocs;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.service.ExternalToolService;
import org.dataverse.marketplace.service.ResourceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/tools")
public class ExternalToolController {

    @Autowired
    private ExternalToolService externalToolService;

    @Autowired
    private ResourceStorageService resourceStorageService;

    /**
     * Method to retrieve all external tools     
     */
    @GetMapping()
    @ExternalToolsAPIDocs.ExternalToolsListDoc
    public ResponseEntity<?> getAllTools() {

        List<ExternalTool> tools = externalToolService.getAllTools();
        ArrayList<ExternalToolDTO> toolDTOs = new ArrayList<>();
        for (ExternalTool tool : tools) {
            toolDTOs.add(new ExternalToolDTO(tool));
        }

        return ResponseEntity.ok(toolDTOs);
    }

    /**
     * Method to add a new external tool
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddExternalToolsRequestDoc
    public ResponseEntity<?> addNewTool(@Valid AddToolRequest addToolRequest) {

        try {
            return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error adding tool",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

    /**
     * Method to retrieve all external tools     
     */
    @GetMapping("/{toolId}")
    @ExternalToolsAPIDocs.GetExternalToolByIdDoc
    public ResponseEntity<?> getToolById(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);
        return ResponseEntity.ok(new ExternalToolDTO(tool));
    }

   
    @GetMapping("/{toolId}/images")
    @ExternalToolsAPIDocs.GetToolImagesDoc
    public ResponseEntity<?> getToolImages(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        List<Long> imagesResourceId = new ArrayList<>();
        for (MarketplaceItemImage image : tool.getImages()) {
            imagesResourceId.add(image.getImageStoredResourceId());
        }
        
        return ResponseEntity.ok(imagesResourceId);
    }

    
    @PostMapping(path = "/{toolId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddToolImagesDoc
    public ResponseEntity<?> addToolImages(
            @PathVariable("toolId") Integer toolId, 
            @RequestBody List<MultipartFile> images) throws IOException {

        ExternalTool tool = new ExternalTool();
        tool.setId(toolId);

        List<MarketplaceItemImage> mktImages = externalToolService.addItemImages(tool, images);

        ArrayList<Long> imagesResourceId = new ArrayList<>();
        
        for(MarketplaceItemImage img : mktImages){
            imagesResourceId.add(img.getImageStoredResourceId());
        }

        ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                "Images added",
                String.format("The images were added to the tool with ID %d.", toolId));    

        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{toolId}/images/{imageId}")
    @ExternalToolsAPIDocs.DeleteToolImageDoc
    public ResponseEntity<?> deleteToolImage(
            @PathVariable("toolId") Integer toolId, 
            @PathVariable("imageId") Integer imageId) {

        try {

            MarketplaceItemImage image =  externalToolService.getItemImage(imageId, toolId);
            resourceStorageService.deleteResourceContent(image.getImageStoredResourceId()); 
            externalToolService.deleteToolImage(image);
            return ResponseEntity.ok(new ServerMessageResponse(HttpStatus.OK,
                    "Image deleted",
                    String.format("The image with ID %d was deleted.", imageId)));

        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error deleting image",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }






}
