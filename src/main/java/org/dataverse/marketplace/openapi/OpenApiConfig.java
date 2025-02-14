package org.dataverse.marketplace.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@OpenAPIDefinition(
    info = @Info(
        title = "Dataverse Marketplace API", 
        version = "1.0", 
        description = "API for Dataverse Marketplace",
        license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0"),
        contact = @Contact(name = "Dataverse Marketplace", email = "support@dataverse.org", url = "https://dataverse.org")        
    ),
    security = @SecurityRequirement(name = "bearerAuth"),
    tags = {
        @Tag(name = "Authentication", description = "User authentication and authorization"),
        @Tag(name = "Security", description = "Security management"),
        @Tag(name = "Users", description = "User management"),
        @Tag(name = "StoredResources", description = "Stored resources management"),
        @Tag(name = "ExternalTools", description = "External tools management")
    }
)
@SecuritySchemes({   
    @SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Bearer token for accessing the API"
    )
})
public class OpenApiConfig {
}
