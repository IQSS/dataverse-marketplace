package edu.harvard.iq.dataverse.marketplace.payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "A representation of the signup request")
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(description = "The username of the user to be signed up.", example = "username")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Schema(description = "The email of the user to be signed up.", example = "user@dataverse.org")
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "The password of the user to be signed up.", example = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
