package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dataverse.marketplace.openapi.samples.ExternalToolSamples;
import org.dataverse.marketplace.openapi.samples.ExternalToolVersionSamples;
import org.dataverse.marketplace.openapi.samples.GenericBusinessSamples;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.ToolVersionMetadataUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

public @interface ExternalToolVersionsAPIDocs {

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tool Version")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Version successfully updated",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on version update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for version update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during version update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Updates the version of the specified external tool.",
                description = "This endpoint updates the version of the specified external tool by id.")    
    @RequestBody(description = "The external tool version update request", 
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ToolVersionMetadataUpdateRequest.class),
                examples = @ExampleObject(ExternalToolVersionSamples.EXTERNAL_TOOL_VERSION_REQUEST_SAMPLE)))
    @Parameter(name = "toolId",
                description = "The id of the external tool",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "versionId",
                description = "The id of the version of the external tool to be updated",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface UpdateVersionByIdDoc{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tool Version")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Version successfully added",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on version add",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for version add",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during version add",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Adds a new version to the specified external tool.",
                description = "This endpoint adds a new version to the specified external tool by id.")    
    @RequestBody(description = "The external tool version add request", 
                required = true, 
                content = @Content(mediaType = "multipart/form-data",
                schema = @Schema(implementation = AddVersionRequest.class),
                examples = @ExampleObject(ExternalToolVersionSamples.ADD_EXTERNAL_TOOL_VERSION_REQUEST_SAMPLE)))
    @Parameter(name = "toolId",
                description = "The id of the external tool to add a new version",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))                
    public @interface AddExternalToolVersionDoc{}

    public @interface GetExternalToolVersionByIdDoc{}

    public @interface DeleteExternalToolVersionByIdDoc{}

    public @interface GetExternalToolVersionsByToolIdDoc{}



}
