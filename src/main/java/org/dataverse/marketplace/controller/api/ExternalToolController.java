package org.dataverse.marketplace.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.startup.Tool;
import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.service.ExternalToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/tools")
public class ExternalToolController {

    @Autowired
    private ExternalToolService externalToolService;

    @GetMapping()
    public ResponseEntity<?> getAllTools() {

        List<ExternalTool> tools = externalToolService.getAllTools();
        ArrayList<ExternalToolDTO> toolDTOs = new ArrayList<>();
        for (ExternalTool tool : tools) {
            toolDTOs.add(new ExternalToolDTO(tool));
        }

        return ResponseEntity.ok(toolDTOs);
    }

    @PostMapping()
    public ResponseEntity<?> addNewTool(@Valid @RequestBody AddToolRequest addToolRequest) throws IOException{
       return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
    }

}
