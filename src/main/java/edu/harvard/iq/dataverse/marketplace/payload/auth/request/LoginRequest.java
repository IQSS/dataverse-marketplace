package edu.harvard.iq.dataverse.marketplace.payload.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Class to model the login request.
 */
@Schema(description = "A representation of the login request")
public class LoginRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "The username of the user to be logged in.", example = "username")
    private String username;

    @NotBlank
    @Size(min = 6)
    @Schema(description = "The password of the user to be logged in.", example = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
