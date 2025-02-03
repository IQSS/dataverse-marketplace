package edu.harvard.iq.dataverse.marketplace.payload.auth;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A representation of the JWT response")
public class JwtResponse {

    @Schema(description = "The bearer token.", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMzUwNjQwOCwiaWF0IjoxNjIzNDk5MjA4fQ.7Z")
    private String accessToken;

    @Schema(description = "The token type.", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "The user id of the authenticated user.", example = "0")
    private Long id;

    @Schema(description = "The username of the authenticated user.", example = "username")
    private String username;

    @Schema(description = "The email of the authenticated user.", example = "user@dataverse.org")
    private String email;

    @Schema(description = "The roles of the authenticated user", example = "[\"ADMIN\"]")
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
