package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.openapi.samples.GenericBusinessSamples;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

public @interface SettingsAPIDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Settings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                        description = "Setting successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Object.class),
                        examples = @ExampleObject(value = "{\"key\": \"registration_enabled\", \"value\": \"true\"}"))),
        @ApiResponse(responseCode = "404",
                        description = "Setting not found",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500",
                        description = "Internal Server Error during setting retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Gets a setting value by key.",
                description = "This public endpoint returns the value of a configuration setting by its key.")
    @io.swagger.v3.oas.annotations.Parameter(name = "key",
                description = "The setting key to retrieve (e.g., 'registration_enabled')",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "string"),
                example = "registration_enabled")
    public @interface GetSetting{}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Settings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                        description = "Setting successfully updated",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400",
                        description = "Bad request on setting update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401",
                        description = "Access Denied - Admin role required",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500",
                        description = "Internal Server Error during setting update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Sets a setting value (Admin only).",
                description = "This endpoint allows administrators to update configuration settings. If the setting doesn't exist, it will be created.")
    @io.swagger.v3.oas.annotations.Parameter(name = "key",
                description = "The setting key to update (e.g., 'registration_enabled')",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "string"),
                example = "registration_enabled")
    @io.swagger.v3.oas.annotations.Parameter(name = "value",
                description = "The new value for the setting",
                required = true,
                in = ParameterIn.QUERY,
                schema = @Schema(type = "string"),
                example = "true")
    public @interface SetSetting{}

}
