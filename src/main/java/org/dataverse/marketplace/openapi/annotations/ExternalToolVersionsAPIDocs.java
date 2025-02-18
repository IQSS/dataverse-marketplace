package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dataverse.marketplace.openapi.samples.ExternalToolSamples;
import org.dataverse.marketplace.openapi.samples.ExternalToolVersionSamples;
import org.dataverse.marketplace.openapi.samples.GenericBusinessSamples;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.payload.ExternalToolManifestDTO;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.ToolVersionMetadataUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

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
    @Tag(name = "External Tools Version")
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
    @Tag(name = "External Tools Version")
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

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Version")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Version by id successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on version by id retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for version by id retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during version by id retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Retrieves the version of the specified external tool by id.",
                description = "This endpoint retrieves the version of the specified external tool by id.")       
    @Parameter(name = "toolId",
                description = "The id of the external tool that contains the version to be retrieved",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "versionId",
                description = "The id of the version of the external tool to be retrieved",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface GetExternalToolVersionByIdDoc{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Version")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Version by id successfully deleted",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on version deletion",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for version deletion",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during version deletion",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Deletes the specified version of the external tool.",
                description = "This endpoint deletes the specified version of the external tool by id. It will fail if you try to delete the only version of a tool.")       
    @Parameter(name = "toolId",
                description = "The id of the external tool that contains the version to be deleted",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "versionId",
                description = "The id of the version of the external tool to be deleted",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface DeleteExternalToolVersionByIdDoc{}

    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Version")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "All versions of the external tool successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on all versions retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for all versions retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during all versions retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Retrieves all versions of the specified external tool.",
                description = "This endpoint retrieves all versions of the specified external tool by id.")       
    @Parameter(name = "toolId",
                description = "The id of the external tool that contains all the versions to be retrieved",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface GetExternalToolVersionsByToolIdDoc{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Version Manifest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "New Manifest successfully added to the version of the external tool",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),    
        @ApiResponse(responseCode = "400", 
                        description = "Bad request when adding a new manifest",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials when adding a new manifest",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error when adding a new manifest",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Adds a new manifest.",
                description = "This endpoint will add a new manifest to the specified version of the external tool.")
    @Parameter(name = "toolId",
                description = "The id of the external tool that contains the version to add a new manifest",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "versionId",
                description = "The id of the version of the external tool that will have a new manifest",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @RequestBody(description = "The request to add a new manifest to an existing version of the external tool", 
                required = true, 
                content = @Content(mediaType = "multipart/form-data",
                schema = @Schema(name = "jsonData", 
                                example = "[\"ask-the-data.json\"]",
                                type = "array",
                                contentMediaType = "application/octet-stream",
                                implementation = MultipartFile[].class)))
    public @interface AddVersionManifestDoc{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Version Manifest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Version manifest list successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExternalToolManifestDTO[].class),
                        examples = @ExampleObject(ExternalToolVersionSamples.EXTERNAL_TOOL_VERSION_MANIFESTS_SAMPLE))),    
        @ApiResponse(responseCode = "400", 
                        description = "Bad request when retrieving the manifest list",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials when retrieving the manifest list",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error when retrieving the manifest list",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Retrieves the manifest list.",
                description = "This endpoint will retrieve the manifest list from the specified version of the external tool.")
    @Parameter(name = "toolId",
                description = "The id of the external tool that contains the version to get the manifest list",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "versionId",
                description = "The id of the version of the external tool to retrieve the manifest list",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface GetVersionManifestDoc{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Version Manifest")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Manifest successfully deleted from the version of the external tool",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),    
        @ApiResponse(responseCode = "400", 
                        description = "Bad request when trying to delete the manifest",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials when when trying to delete the manifest",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error when when trying to delete the manifest",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Deletes the manifest.",
                description = """
                    This endpoint will delete the specified manifest from the specified version of the external tool. 
                    It will fail if you try to delete the only manifest of a version.
                    """)
    @Parameter(name = "toolId",
                description = "The id of the external tool that contains the version to delete a manifest",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "versionId",
                description = "The id of the version of the external tool that contains the manifest to be deleted",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    @Parameter(name = "manifestId",
                description = "The id of the manifest to be deleted",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface DeleteVersionManifestDoc{}



}
