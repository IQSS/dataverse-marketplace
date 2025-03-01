package org.dataverse.marketplace.controller.api;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.annotations.ExternalToolsAPIDocs;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.security.ApplicationRoles;
import org.dataverse.marketplace.service.ExternalToolService;
import org.dataverse.marketplace.service.ResourceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
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

        return ResponseEntity.ok(externalToolService.getAllTools());
    }

    /**
     * Method to add a new external tool
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddExternalToolsRequestDoc
    public ResponseEntity<?> addNewTool(@Valid AddToolRequest addToolRequest) {

        try {
            return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
        } catch (IOException e) {
            ServerMessageResponse messageResponse 
                = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
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

    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @PutMapping("/{toolId}")
    @ExternalToolsAPIDocs.UpdateExternalToolDoc
    public ResponseEntity<?> updateTool(@PathVariable("toolId") Integer toolId, 
                                        @Valid @RequestBody UpdateToolRequest updateToolRequest) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if(tool == null){
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }

        try {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.OK,
                    "Tool updated",
                    String.format("The tool with ID %d was updated.", toolId));
            externalToolService.updateTool(tool, updateToolRequest);
            return ResponseEntity.ok(messageResponse);
        } catch (Exception e) {
            ServerMessageResponse messageResponse 
                = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                            "Error updating tool",
                                            e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

   
    /**
     * Method to retrieve the images of an external tool.
     */
    @GetMapping("/{toolId}/images")
    @ExternalToolsAPIDocs.GetToolImagesDoc
    public ResponseEntity<?> getToolImages(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if(tool == null){
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }

        List<MarketplaceItemImageDTO> imagesResourceId = new ArrayList<>();
        for (MarketplaceItemImage image : tool.getImages()) {
            imagesResourceId.add(new MarketplaceItemImageDTO(image));
        }
        
        return ResponseEntity.ok(imagesResourceId);
    }

    /**
     * Method to add images to an external tool.
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @PostMapping(path = "/{toolId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddToolImagesDoc
    public ResponseEntity<?> addToolImages(
            @PathVariable("toolId") Integer toolId, 
            @RequestBody List<MultipartFile> images) throws IOException {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if(tool == null){
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
        
        try {
            List<MarketplaceItemImage> addedImages = externalToolService.addItemImages(tool, images);
            ArrayList<MarketplaceItemImageDTO> imagesList = new ArrayList<>();
            for (MarketplaceItemImage image : addedImages) {
                imagesList.add(new MarketplaceItemImageDTO(image));
            }
            return ResponseEntity.ok(imagesList);
            
        } catch (Exception e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error adding images",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);

        }
    }

    /**
     * Method to delete an image from an external tool.
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE)
    @DeleteMapping("/{toolId}/images/{imageId}")
    @ExternalToolsAPIDocs.DeleteToolImageDoc
    public ResponseEntity<?> deleteToolImage(
            @PathVariable("toolId") Integer toolId, 
            @PathVariable("imageId") Integer imageId) {

        MarketplaceItemImage image =  externalToolService.getItemImage(imageId, toolId);

        if(image == null){
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested image with ID %d was not found.", imageId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }

        try {

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
