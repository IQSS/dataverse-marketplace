package edu.harvard.iq.dataverse.marketplace.openapi.samples;

public abstract class GenericBusinessSamples {

    public static final String SERVER_RESPONSE_400 = """
        {
            "timestamp": "2024-11-01T19:39:15.953+00:00",
            "code": 400,
            "status": "Not Found",
            "message": "No static resource api/unknown.",
            "details": "Details on SERVER_RESPONSE_400"
        }
        """;
    
    public static final String SERVER_RESPONSE_500 = """
        {
            "timestamp": "2024-11-01T19:42:40.470+00:00",
            "code": 500,
            "status": "Internal Server Error",
            "message": "Not implemented",
            "details": "Details on SERVER_RESPONSE_500"
        }
        """;

    public static final String SERVER_RESPONSE_401 = """
        {
            "timestamp": "2024-11-01T19:45:15.953+00:00",
            "code": 401,
            "status": "Unauthorized",
            "message": "Bad credentials",
            "details": "Details on SERVER_RESPONSE_401"
        }
        """;

}
