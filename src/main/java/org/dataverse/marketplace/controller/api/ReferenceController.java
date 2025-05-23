package org.dataverse.marketplace.controller.api;

import java.util.Arrays;
import java.util.List;

import org.dataverse.marketplace.model.enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/reference")
public class ReferenceController {

    @GetMapping("/http-methods")
    public HttpMethod[] getHttpMethods() {
        return HttpMethod.values();
    }

    @GetMapping("/scopes")
    public List<String> getScopes() {
        return Arrays.stream(Scope.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }

    @GetMapping("/tool-types")
    public List<String> getToolTypes() {
        return Arrays.stream(ToolType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }

}
