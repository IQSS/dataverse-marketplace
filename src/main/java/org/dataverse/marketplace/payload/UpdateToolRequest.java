package org.dataverse.marketplace.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request to update an external tool")
public class UpdateToolRequest {

    @Schema(description = "Name of the external tool to be updated", 
        example = "Ask the data")
    @NotEmpty
    private String name;

    @Schema(description = "Description of the external tool to be updated", 
        example = "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.")    
    @NotEmpty
    private String description;

    /* Getters and Setters */

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
