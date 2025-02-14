package org.dataverse.marketplace.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.openapi.annotations.ExternalToolsAPIDocs;
import org.dataverse.marketplace.openapi.annotations.ExternalToolsAPIDocs.ExternalToolsList;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.service.ExternalToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/tools")
public class ExternalToolController {

    @Autowired
    private ExternalToolService externalToolService;

    /**
     * Method to retrieve all external tools     
     */
    @GetMapping()
    @ExternalToolsAPIDocs.ExternalToolsList
    public ResponseEntity<?> getAllTools() {

        List<ExternalTool> tools = externalToolService.getAllTools();
        ArrayList<ExternalToolDTO> toolDTOs = new ArrayList<>();
        for (ExternalTool tool : tools) {
            toolDTOs.add(new ExternalToolDTO(tool));
        }

        return ResponseEntity.ok(toolDTOs);
    }

     /**
     * Method to retrieve all external tools     
     */
    @GetMapping("/{toolId}")
    @ExternalToolsAPIDocs.GetExternalToolById
    public ResponseEntity<?> getToolById(@PathVariable("toolId") Integer toolId) {

        ExternalTool tool = externalToolService.getToolById(toolId);
        return ResponseEntity.ok(new ExternalToolDTO(tool));
    }

    /**
     * Method to add a new external tool
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ExternalToolsAPIDocs.AddExternalToolsRequest
    public ResponseEntity<?> addNewTool(@Valid @RequestBody AddToolRequest addToolRequest) throws IOException{
        
        return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
    }

}
