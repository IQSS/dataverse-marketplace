package org.dataverse.marketplace.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.io.IOException;

import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.repository.ExternalToolRepo;
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
        return ResponseEntity.ok(externalToolService.getAllTools());
    }

    @PostMapping()
    public ResponseEntity<?> addNewTool(@Valid @RequestBody AddToolRequest addToolRequest) throws IOException{
       return ResponseEntity.ok(externalToolService.addTool(addToolRequest));
    }

}
