package org.dataverse.marketplace.openapi.samples;

public class ExternalToolVersionSamples {

    public static final String EXTERNAL_TOOL_VERSIONS_LIST_SAMPLE = """
            [
                {
                    "id": 1,
                    "version": "1",
                    "releaseNote": "This release includes a new feature that allows you to ask questions to an LLM.",
                    "dataverseMinVersion": "6",
                    "manifests": [
                    {
                        "manifestId": 2,
                        "storedResourceId": 6
                    }
                    ]
                }
            ]
            """;

    public static final String EXTERNAL_TOOL_VERSION_REQUEST_SAMPLE = """
        {
            "releaseNote": "This release includes a new feature that allows you to ask questions to an LLM.",
            "version": "1.0",
            "dvMinVersion": "6.0"
        }
    """;

    public static final String ADD_EXTERNAL_TOOL_VERSION_REQUEST_SAMPLE = """
        {
            "releaseNote": "This release includes a new feature that allows you to ask questions to an LLM.",
            "version": "1.0",
            "dvMinVersion": "6.0",
            "jsonData": [
                "ask-the-data.json"
            ]
        }
    """;

    public static final String EXTERNAL_TOOL_VERSION_MANIFESTS_SAMPLE = """
        [   
          {
            "manifestId": 1,
            "storedResourceId": 4
          },
          {
            "manifestId": 2,
            "storedResourceId": 6
          }
        ]
    """;


}
