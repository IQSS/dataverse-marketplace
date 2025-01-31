package edu.harvard.iq.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import edu.harvard.iq.dataverse.marketplace.payload.ServerMessageResponse;
import edu.harvard.iq.dataverse.marketplace.payload.auth.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import edu.harvard.iq.dataverse.marketplace.openapi.samples.AuthAPISamples;
import edu.harvard.iq.dataverse.marketplace.openapi.samples.GenericBusinessSamples;

public @interface AuthAPIDocs {

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "auth", description = "Dataverse Marketplace Authentication API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "User successfully logged in",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JwtResponse.class),
                        examples = @ExampleObject(AuthAPISamples.JWT))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on user login",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_RESPONSE_400))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on user login",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_RESPONSE_401))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during user login",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_RESPONSE_500)))
    })
    @Operation(summary = "Logs in a user and returns a JWT token",
                description = "This endpoint logs in a user and returns a JWT token to be used in subsequent requests.")
    @RequestBody(description = "The login request", 
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = edu.harvard.iq.dataverse.marketplace.payload.auth.LoginRequest.class),
                examples = @ExampleObject(AuthAPISamples.LOGIN_REQUEST)))
    public @interface Login {}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "auth", description = "Dataverse Marketplace User creation API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "User successfully signed up",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = JwtResponse.class),
                        examples = @ExampleObject(AuthAPISamples.JWT))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on user signup",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_RESPONSE_400))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for user signup",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_RESPONSE_401))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during user SIGNUP",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_RESPONSE_500)))                        

    })
    @Operation(summary = "Creates a new user.",
                description = "This endpoint creates a new user in the system with no authorities attached.")
    @RequestBody(description = "The signup request", 
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = edu.harvard.iq.dataverse.marketplace.payload.auth.SignupRequest.class),
                examples = @ExampleObject(AuthAPISamples.SIGNUP_REQUEST)))
    public @interface Signup {}

}
