package org.dataverse.marketplace.controller.api;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dataverse.marketplace.model.*;
import org.dataverse.marketplace.openapi.annotations.ExternalToolVersionsAPIDocs;
import org.dataverse.marketplace.openapi.annotations.ExternalToolsAPIDocs;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.repository.UserRepo;
import org.dataverse.marketplace.security.ApplicationRoles;
import org.dataverse.marketplace.service.ExternalToolService;
import org.dataverse.marketplace.service.ExternalToolVersionService;
import org.dataverse.marketplace.service.ResourceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/tools")
public class ExternalToolController {

    @Autowired
    private ExternalToolService externalToolService;

    @Autowired
    private ExternalToolVersionService externalToolVersionService;

    @Autowired
    private ResourceStorageService resourceStorageService;

    @Autowired
    private UserRepo userRepository;

    /**
     * Method to retrieve all external tools
     */
    @GetMapping()
    @ExternalToolsAPIDocs.ExternalToolsListDoc
    public ResponseEntity<?> getAllTools() {

        return ResponseEntity.ok(externalToolService.getAllTools());
    }

    /**
     * Method to retrieve an external tool by id
     */
    @GetMapping("/{toolId}")
    @ExternalToolsAPIDocs.GetExternalToolByIdDoc
    public ResponseEntity<?> getToolById(@PathVariable("toolId") Long toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);
        return ResponseEntity.ok(new ExternalToolDTO(tool));
    }

    /**
     * Methods to add a new external tool
     */

    // with no immage (documented)
    @PreAuthorize("isAuthenticated()")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExternalToolsAPIDocs.AddExternalToolsRequestDoc
    public ResponseEntity<?> addNewTool(
            @RequestBody AddToolRequest addToolRequest) {
        return addTool(addToolRequest);
    }

    // with images (TODO: currently undocumented until can work in swagger)
    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/with-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewToolWithImages(
            @RequestPart("toolData") AddToolRequest addToolRequest,
            @RequestPart(name = "itemImages", required = false) MultipartFile[] itemImages) {
        // add the images to the request object
        if (itemImages != null) {
            addToolRequest.setItemImages(Arrays.asList(itemImages));
        }
        return addTool(addToolRequest);
    }

    private ResponseEntity<?> addTool(AddToolRequest addToolRequest) {
        try {
            String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
            // TODO: this should call a service bean, not repo directly
            User user = userRepository.findByUsername(authenticatedUser).orElse(null);

            return ResponseEntity.ok(externalToolService.addTool(addToolRequest, user));
        } catch (IOException e) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error adding tool",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

    /**
     * Method to update an external tool
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE
            + " or (" + "isAuthenticated()"
            + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @PutMapping("/{toolId}")
    @ExternalToolsAPIDocs.UpdateExternalToolDoc
    public ResponseEntity<?> updateTool(@PathVariable("toolId") Long toolId,
            @Valid @RequestBody UpdateToolRequest updateToolRequest) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if (tool == null) {
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
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error updating tool",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }

    /**
     * Method to delete an an external tool
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE
            + " or (" + "isAuthenticated()"
            + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @DeleteMapping("/{toolId}")
    @ExternalToolsAPIDocs.DeleteExternalToolDoc
    public ResponseEntity<?> deleteTool(
            @PathVariable("toolId") Long toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if (tool == null) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }

        try {
            // first delete the images (
            for (MarketplaceItemImage image : tool.getImages()) {

                resourceStorageService.deleteResourceContent(image.getStoredResource());
                externalToolService.deleteToolImage(image);
            }

            externalToolService.deleteTool(tool);

            return ResponseEntity.ok(new ServerMessageResponse(HttpStatus.OK,
                    "Tool deleted.",
                    String.format("The tool with ID %d was deleted.", toolId)));

        } catch (IOException e) {

            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error deleting tool",
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
        }
    }







    /**
     * Method to retrieve the images of an external tool.
     */
    @GetMapping("/{toolId}/images")
    @ExternalToolsAPIDocs.GetToolImagesDoc
    public ResponseEntity<?> getToolImages(@PathVariable("toolId") Long toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if (tool == null) {
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
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE
            + " or (" + "isAuthenticated()"
            + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @CacheEvict(value = "externalTools", allEntries = true)
    @PostMapping(path = "/{toolId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddToolImagesDoc
    public ResponseEntity<?> addToolImages(
            @PathVariable("toolId") Long toolId,
            @Parameter(name = "images", description = "The images to be added to the external tool", required = true, in = ParameterIn.DEFAULT, schema = @Schema(type = "string", format = "binary", example = "['image.png']"))
            @RequestPart("images") List<MultipartFile> images) throws IOException {

        ExternalTool tool = externalToolService.getToolById(toolId);

        if (tool == null) {
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
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE
            + " or (" + "isAuthenticated()"
            + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @DeleteMapping("/{toolId}/images/{imageId}")
    @ExternalToolsAPIDocs.DeleteToolImageDoc
    public ResponseEntity<?> deleteToolImage(
            @PathVariable("toolId") Long toolId,
            @PathVariable("imageId") Long imageId) {

        MarketplaceItemImage image = externalToolService.getItemImage(imageId);

        if (image == null) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested image with ID %d was not found.", imageId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }

        try {

            resourceStorageService.deleteResourceContent(image.getStoredResource());
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

    /**
     * Method to add a new version to an existing external tool
     */
    @PreAuthorize(ApplicationRoles.ADMIN_ROLE
            + " or (" + "isAuthenticated()"
            + " and @externalToolService.getToolById(#toolId).getOwner().getId() == authentication.getPrincipal().getId)")
    @CacheEvict(value = "externalTools", allEntries = true)
    @PostMapping(path = "/{toolId}/versions", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ExternalToolVersionsAPIDocs.AddExternalToolVersionDoc
    public ResponseEntity<?> addNewExternalToolVersion(
            @PathVariable("toolId") Long toolId,
            @RequestBody AddVersionRequest addVersionRequest) {

        ExternalTool tool = externalToolService.getToolById(toolId);
        if (tool == null) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        } else {

            try {
                ExternalTool externalTool = externalToolService.getToolById(toolId);
                ExternalToolVersion newVersion = externalToolVersionService.addToolVersion(addVersionRequest,
                        externalTool);
                return ResponseEntity.ok(new ExternalToolVersionDTO(newVersion));
            } catch (IOException e) {
                ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error adding version",
                        String.format("An error occurred while adding a new version for the tool with ID %d.", toolId));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageResponse);
            }
        }

    }

    /**
     * Method to retrieve all external tool versions
     */
    @GetMapping("/{toolId}/versions")
    @ExternalToolVersionsAPIDocs.GetExternalToolVersionsByToolIdDoc
    public ResponseEntity<?> getVersionsToolByToolId(
            @PathVariable("toolId") Long toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);
        if (tool == null) {
            ServerMessageResponse messageResponse = new ServerMessageResponse(HttpStatus.NOT_FOUND,
                    "Resource not found",
                    String.format("The requested external tool with ID %d was not found.", toolId));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
        }
        List<ExternalToolVersionDTO> versions = new ArrayList<>();
        for (ExternalToolVersion version : tool.getExternalToolVersions()) {
            versions.add(new ExternalToolVersionDTO(version));
        }

        return ResponseEntity.ok(versions);
    }

}
