package edu.harvard.iq.dataverse.marketplace.payload.auth;

import edu.harvard.iq.dataverse.marketplace.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "A representation of the role creation response")
public class RoleCreationResponse {

    @NotBlank
    @Size(min = 3, max = 20)    
    @Schema(description = "The name of the role succesfully created.", example = "ADMIN")    
    String roleName;

    @NotNull
    @Schema(description = "The id of the role succesfully created.")
    Integer roleId;

    public RoleCreationResponse(Role role) {
        this.roleName = role.getName();
        this.roleId = role.getId();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
