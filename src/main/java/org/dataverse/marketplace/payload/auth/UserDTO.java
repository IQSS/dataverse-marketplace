package org.dataverse.marketplace.payload.auth;

import java.io.Serializable;

import org.dataverse.marketplace.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User", description = "A representation of the user")
public class UserDTO implements Serializable{

    @Schema(description = "The id of the user", example = "1")
    private Long id;

    @Schema(description = "The username of the user", example = "admin")
    private String username;

    @Schema(description = "The email of the user", example = "user@dataverse.org")
    private String email;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
