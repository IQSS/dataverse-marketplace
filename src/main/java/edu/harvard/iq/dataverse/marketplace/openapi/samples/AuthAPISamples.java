package edu.harvard.iq.dataverse.marketplace.openapi.samples;

public abstract class AuthAPISamples {
    public static final String JWT = """
        {
            "id": 0,
            "username": "username",
            "email": "username@dataverse.org",
            "roles": [
                "ADMIN"
            ],
            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqcHRvc2NhIiwiaWF0IjoxNzM4MzQzMDcyLCJleHAiOjE3Mzg0Mjk0NzJ9.6phCugWMc5iyogAGrWWRo4G1RtsPLuwJ2yYKpdxWO9E",
            "tokenType": "Bearer"
        }
        """;

    public static final String LOGIN_REQUEST = """
        {
            "username": "username",
            "password": "password"
        }
        """;

    public static final String SIGNUP_REQUEST = """
        {
            "username": "username",
            "email":"email@dataverse.org.com",
            "password":"123456"
        }
        """;

    public static final String ROLE_CREATION_REQUEST = """
        {
            "roleName": "ADMIN"
        }
        """;
    
    public static final String ROLE_CREATION_RESPONSE = """
        {
            "roleName": "ADMIN",
            "roleId": 0
        }
        """;

}


