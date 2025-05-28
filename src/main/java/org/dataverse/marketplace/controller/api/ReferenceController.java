package org.dataverse.marketplace.controller.api;

import java.util.Arrays;
import java.util.List;

import org.dataverse.marketplace.model.enums.*;
import org.dataverse.marketplace.openapi.annotations.ReferenceControllerAPIDocs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reference")
public class ReferenceController {

    @GetMapping("/http-methods")
    @ReferenceControllerAPIDocs.GetHttpMethods
    public HttpMethod[] getHttpMethods() {
        return HttpMethod.values();
    }

    @GetMapping("/scopes")
    @ReferenceControllerAPIDocs.GetScopes
    public List<String> getScopes() {
        return Arrays.stream(Scope.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }

    @GetMapping("/tool-types")
    @ReferenceControllerAPIDocs.GetToolTypes
    public List<String> getToolTypes() {
        return Arrays.stream(ToolType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }

}
