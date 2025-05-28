package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dataverse.marketplace.openapi.samples.GenericBusinessSamples;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

public @interface ReferenceControllerAPIDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Reference")
    @Operation(summary = "Retrieves HTTP methods", description = "This endpoint returns a list of supported HTTP methods.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "HTTP methods successfully retrieved",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = HttpMethod[].class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error retrieving HTTP methods",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ServerMessageResponse.class)))
    })
    public @interface GetHttpMethods {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Reference")
    @Operation(summary = "Retrieves scopes", description = "This endpoint returns a list of available scopes.") 
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Scopes successfully retrieved",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = String[].class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error retrieving scopes",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ServerMessageResponse.class)))
    })
    public @interface GetScopes {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Reference")
    @Operation(summary = "Retrieves tool types", description = "This endpoint returns a list of available tool types.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tool types successfully retrieved",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = String[].class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error retrieving tool types",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = ServerMessageResponse.class)))
    })
    public @interface GetToolTypes {}


    
}
