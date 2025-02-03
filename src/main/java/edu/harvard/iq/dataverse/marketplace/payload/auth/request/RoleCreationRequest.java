package edu.harvard.iq.dataverse.marketplace.payload.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "A representation of the role creation request")
public class RoleCreationRequest {

    @NotBlank
    @Size(min = 3, max = 20)    
    @Schema(description = "The name of the role to be created.", example = "ADMIN")
    String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
