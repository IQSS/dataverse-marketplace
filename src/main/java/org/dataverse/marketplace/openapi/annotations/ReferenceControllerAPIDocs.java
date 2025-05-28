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
        @ApiResponse(responseCode = "200", 
                        description = "HTTP Methods successfully retrieved",
                        content = @Content(mediaType = "application/octet-stream",
                        schema = @Schema(implementation = Resource.class),
                        examples = @ExampleObject("SGVsbG8sIFdvcmxkIQ=="))),
        @ApiResponse(responseCode = "404", 
                        description = "Resource not found when trying to retrieve HTTP Methods",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on retrieving HTTP Methods",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400",
                        description = "Bad request on retrieving HTTP Methods",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error retrieving HTTP Methods",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    public @interface GetHttpMethods {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Reference")
    @Operation(summary = "Retrieves scopes", description = "This endpoint returns a list of available scopes.") 
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Scopes successfully retrieved",
                        content = @Content(mediaType = "application/octet-stream",
                        schema = @Schema(implementation = Resource.class),
                        examples = @ExampleObject("SGVsbG8sIFdvcmxkIQ=="))),
        @ApiResponse(responseCode = "404", 
                        description = "Resource not found when trying to retrieve Scopes",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on retrieving Scopes",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400",
                        description = "Bad request on retrieving Scopes",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error retrieving Scopes",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    public @interface GetScopes {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Reference")
    @Operation(summary = "Retrieves tool types", description = "This endpoint returns a list of available tool types.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Tool Types successfully retrieved",
                        content = @Content(mediaType = "application/octet-stream",
                        schema = @Schema(implementation = Resource.class),
                        examples = @ExampleObject("SGVsbG8sIFdvcmxkIQ=="))),
        @ApiResponse(responseCode = "404", 
                        description = "Resource not found when trying to retrieve Tool Types",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on retrieving Tool Types",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400",
                        description = "Bad request on retrieving Tool Types",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error retrieving Tool Types",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    public @interface GetToolTypes {}


    
}
