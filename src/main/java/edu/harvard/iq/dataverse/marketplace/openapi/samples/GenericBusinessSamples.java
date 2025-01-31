package edu.harvard.iq.dataverse.marketplace.openapi.samples;

public abstract class GenericBusinessSamples {

    public static final String SERVER_RESPONSE_400 = """
        {
            "timestamp": "2024-11-01T19:39:15.953+00:00",
            "code": 400,
            "status": "Not Found",
            "message": "No static resource api/unknown.",
            "stackTrace": "org.springframework.web.servlet.resource.ResourceHttpRequestHandler.handleRequest(ResourceHttpRequestHandler.java:585)"
        }
        """;
    
    public static final String SERVER_RESPONSE_500 = """
        {
            "timestamp": "2024-11-01T19:42:40.470+00:00",
            "code": 500,
            "status": "Internal Server Error",
            "message": "Not implemented",
            "stackTrace": "edu.harvard.iq.dataverse_hub.controller.api.InstallationController.getInstallationsAPIController(InstallationController.java:54)"
        }
        """;

    public static final String SERVER_RESPONSE_401 = """
        {
            "timestamp": "2024-11-01T19:45:15.953+00:00",
            "code": 401,
            "status": "Unauthorized",
            "message": "Bad credentials",
            "stackTrace": "org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.authenticate(AbstractUserDetailsAuthenticationProvider.java:141)"
        }
        """;

}
