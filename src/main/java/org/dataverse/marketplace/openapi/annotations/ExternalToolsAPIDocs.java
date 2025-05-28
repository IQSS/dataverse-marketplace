package org.dataverse.marketplace.openapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dataverse.marketplace.openapi.samples.GenericBusinessSamples;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.payload.MarketplaceItemImageDTO;
import org.dataverse.marketplace.payload.ServerMessageResponse;
import org.dataverse.marketplace.payload.UpdateToolRequest;
import org.dataverse.marketplace.payload.auth.UserDTO;
import org.dataverse.marketplace.openapi.samples.AuthAPISamples;
import org.dataverse.marketplace.openapi.samples.ExternalToolSamples;

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

public @interface ExternalToolsAPIDocs {

        @Target({ ElementType.METHOD })
        @Retention(RetentionPolicy.RUNTIME)
        @Tag(name = "External Tools")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "External tools successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExternalToolDTO[].class), examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOLS_LIST_SAMPLE))),
                        @ApiResponse(responseCode = "400", description = "Bad request on External tools list retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
                        @ApiResponse(responseCode = "401", description = "Bad credentials on External tools list retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error during External tools list retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
        })
        @Operation(summary = "Returns a list of all external tools", description = "This endpoint will return a list of all external tools available in the marketplace.")
        public @interface ExternalToolsListDoc {
        }

        @Target({ ElementType.METHOD })
        @Retention(RetentionPolicy.RUNTIME)
        @Tag(name = "External Tools")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Add new external tool", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExternalToolDTO.class), examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOL_SINGLE_SAMPLE))),
                        @ApiResponse(responseCode = "400", description = "Bad request when adding a new external tool", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
                        @ApiResponse(responseCode = "401", description = "Bad credentials when adding a new external tool", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error when adding a new external tool", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
        })
        @Operation(summary = "Adds a new external tool", description = "This endpoint will add a new external tool to the marketplace.")
        @RequestBody(description = "The external tool creation request", required = true, content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = AddToolRequest.class), examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOL_MULTIPART_FORM_SAMPLE)))
        public @interface AddExternalToolsRequestDoc {
        }

        @Target({ ElementType.METHOD })
        @Retention(RetentionPolicy.RUNTIME)
        @Tag(name = "External Tools")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "External Tool successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class), examples = @ExampleObject(AuthAPISamples.USER))),
                        @ApiResponse(responseCode = "400", description = "Bad request on External Tool retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
                        @ApiResponse(responseCode = "401", description = "Access Denied for External Tool retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error during External Tool retrieval", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
        })
        @Operation(summary = "Retrieves the information from the specified external tool.", description = "This endpoint retrieves the information from the specified external tool by id.")
        @Parameter(name = "toolId", description = "The id of the external tool to be retrieved", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer", format
    public @interface GetExternalToolByIdDoc {
    }

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "External Tool Image Resource-IDs list successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long[].class), examples = @ExampleObject("[1, 2, 3]"))),
            @ApiResponse(responseCode = "400", description = "Bad request on External Tool Image Resource-IDs list successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "Access Denied for External Tool Image Resource-IDs list successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during External Tool Image Resource-IDs list successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Retrieves the list of image resource-ids from the specified external tool.", description = "This endpoint retrieves the list of image resource-ids from the specified external tool by id.")
    @Parameter(name = "toolId", description = "The id of the external tool to retrieve the image resource-ids list", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer"))    
    public @interface GetToolImagesDoc {
    }

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "External Tool Image successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarketplaceItemImageDTO.class), examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOL_IMAGE_SAMPLE))),
            @ApiResponse(responseCode = "400", description = "Bad request on External Tool Image successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "Access Denied for External Tool Image successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during External Tool Image successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Adds a new image to the specified external tool.", description = "This endpoint adds a new image to the specified external tool by id.")
    @Parameter(name = "toolId", description = "the id of the external tool to add the image", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer"))        
    @RequestBody(description = "The add tool image body request")
    public @interface AddToolImagesDoc {
    }

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools Images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "External Tool Image successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "Bad request on External Tool Image successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "Access Denied for External Tool Image successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during External Tool Image successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Deletes the specified external tool image.", description = "This endpoint deletes the specified external tool image by id.")
    @Parameter(name = "toolId", description = "The id of the external tool that contains the image to be deleted", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer"))
    @Parameter(name = "imageId", description = "The id of the image to be deleted", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer"))
    public @interface DeleteToolImageDoc {
    }

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "External Tool successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "Bad request on External Tool update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "Access Denied for External Tool update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during External Tool update", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Update the specified external tool.", description = "This endpoint updates the specified external tool.")
    @Parameter(name = "toolId", description = "The id of the external tool that contains the image to be updated", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer"))
    @RequestBody(description = "The external tool update request", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateToolRequest.class), examples = @ExampleObject(ExternalToolSamples.UPDATE_EXTERNAL_TOOL_REQUEST)))
    public @interface UpdateExternalToolDoc {
    }

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "External Tool successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "400", description = "Bad request on External Tool delete", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "Access Denied for External Tool delete", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error during External Tool delete", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Delete the specified external tool.", description = "This endpoint deletes the specified external tool.")
    @Parameter(name = "toolId", description = "The id of the external tool to be deleted", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer"))
    public @interface DeleteExternalToolDoc {
    }   
    

    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Tag(name = "External Tools")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success on GetExternalToolByOwner", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExternalToolDTO[].class), examples = @ExampleObject(ExternalToolSamples.EXTERNAL_TOOLS_LIST_SAMPLE))),
            @ApiResponse(responseCode = "400", description = "Bad request on GetExternalToolByOwner", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "401", description = "Bad credentials on tools on GetExternalToolByOwner", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error on GetExternalToolByOwner ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerMessageResponse.class), examples = @ExampleObject(GenericBusinessSamples.SERVER_MESSAGE_RESPONSE)))
    })
    @Operation(summary = "Returns a list of all external tools by owner id", description = "This endpoint will return a list of all external tools owned by a specific user.")
    public @interface GetExternalToolByOwnerIdDoc {
    }

}
