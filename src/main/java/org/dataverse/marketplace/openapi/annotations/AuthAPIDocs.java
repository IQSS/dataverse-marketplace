package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.dataverse.marketplace.payload.*;
import org.dataverse.marketplace.payload.auth.*;
import org.dataverse.marketplace.payload.auth.response.*;
import org.dataverse.marketplace.openapi.samples.*;
import java.lang.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

public @interface AuthAPIDocs {

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Authentication")
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
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Bad credentials on user login",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during user login",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Logs in a user and returns a JWT token",
                description = "This endpoint logs in a user and returns a JWT token to be used in subsequent requests.")
    @RequestBody(description = "The login request", 
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = org.dataverse.marketplace.payload.auth.request.LoginRequest.class),
                examples = @ExampleObject(AuthAPISamples.LOGIN_REQUEST)))
    public @interface Login {}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Authentication")
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
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for user signup",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during user signup",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Creates a new user.",
                description = "This endpoint creates a new user in the system with no authorities attached.")
    @RequestBody(description = "The signup request", 
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = org.dataverse.marketplace.payload.auth.request.SignupRequest.class),
                examples = @ExampleObject(AuthAPISamples.SIGNUP_REQUEST)))
    public @interface Signup {}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Security")    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Role successfully created",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = RoleCreationResponse.class),
                        examples = @ExampleObject(AuthAPISamples.ROLE_CREATION_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on role creation",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for role creation",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during role creation",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Creates a new role.",
                description = "This endpoint creates a new role in the system.")
    @RequestBody(description = "The role creation request", 
                required = true, 
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = org.dataverse.marketplace.payload.auth.request.RoleCreationRequest.class),
                examples = @ExampleObject(AuthAPISamples.ROLE_CREATION_REQUEST)))
    public @interface RoleCreationRequest{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Security")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Role successfully assigned",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on role assignment",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for role assignment",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during role assignment",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Assigns a role.",
                description = "This endpoint assigns a role to a user.")
    @Parameter(name = "userId", 
                description = "The user id to assign the role to", 
                required = true, 
                in = ParameterIn.PATH, 
                schema = @Schema(type = "integer", format = "int64"))
    @Parameter(name = "roleId",
                description = "The role id to assign to the user.",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface AssignRole{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Security")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Role successfully removed",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on role removal",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for role removal",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during role removal",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Removes a role from the user.",
                description = "This endpoint removes a role from an user.")
    @Parameter(name = "userId", 
                description = "The user id to remove the role from", 
                required = true, 
                in = ParameterIn.PATH, 
                schema = @Schema(type = "integer", format = "int64"))
    @Parameter(name = "roleId",
                description = "The role id to be removed from the user",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer"))
    public @interface RemoveRole{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Security")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Password successfully updated",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on password update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for password update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during password update",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Changes the password of the user.",
                description = "This endpoint changes the password of the user, a user can only change it's own password.")
    @Parameter(name = "password", 
                description = "The new password to be set", 
                required = true, 
                in = ParameterIn.HEADER, 
                schema = @Schema(type = "string"),
                example = "newpassword")
    public @interface ChangePassword{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Security")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "Role list successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = RoleDTO[].class),
                        examples = @ExampleObject(AuthAPISamples.ROLES))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on role list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for role list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during role list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Retrieves the list of roles.",
                description = "This endpoint retrieves the list of roles in the system.")    
    public @interface GetRoles{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "User list successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserDTO[].class),
                        examples = @ExampleObject(AuthAPISamples.USERS))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on user list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for user list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during user list retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Retrieves the list of users.",
                description = "This endpoint retrieves the list of users in the system.")    
    public @interface GetUsers{}

    @Target({ElementType.METHOD})    
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                        description = "User successfully retrieved",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UserDTO.class),
                        examples = @ExampleObject(AuthAPISamples.USER))),
        @ApiResponse(responseCode = "400", 
                        description = "Bad request on user retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "401", 
                        description = "Access Denied for user retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
        @ApiResponse(responseCode = "500", 
                        description = "Internal Server Error during user retrieval",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ServerMessageResponse.class),
                        examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))                        
    })
    @Operation(summary = "Retrieves the information from the specified user.",
                description = "This endpoint retrieves the information from the specified user.")    
    @Parameter(name = "userId",
                description = "The id of the user to be retrieved",
                required = true,
                in = ParameterIn.PATH,
                schema = @Schema(type = "integer", format = "int64"))
    public @interface GetUser{}

}
