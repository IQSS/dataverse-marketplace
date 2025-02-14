package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dataverse.marketplace.openapi.samples.GenericBusinessSamples;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.dataverse.marketplace.openapi.samples.ExternalToolSamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

public @interface ExternalToolsAPIDocs {

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "ExternalTools")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "External tools successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExternalToolDTO[].class),
                        examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOLS_LIST_SAMPLE))),    
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on External tools list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on External tools list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during External tools list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Returns a list of all external tools",
                description = "This endpoint will return a list of all external tools available in the marketplace.")
    public @interface ExternalToolsList {}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "ExternalTools")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Add new external tool",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ExternalToolDTO.class),
                        examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOLS_LIST_SAMPLE))),    
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on External tools list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on External tools list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during External tools list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Adds a new external tool",
                description = "This endpoint will add a new external tool to the marketplace."
               )
    public @interface AddExternalToolsRequest {}

}
