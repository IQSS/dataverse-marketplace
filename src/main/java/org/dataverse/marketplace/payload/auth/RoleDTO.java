package org.dataverse.marketplace.payload.auth;

import org.dataverse.marketplace.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Role", description = "A representation of the role")
public class RoleDTO {

    @Schema(description = "The id of the role", example = "1")
    private Long id;

    @Schema(description = "The name of the role", example = "ADMIN")
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
