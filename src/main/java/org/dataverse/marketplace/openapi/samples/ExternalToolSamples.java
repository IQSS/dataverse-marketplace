package org.dataverse.marketplace.openapi.samples;

public abstract class ExternalToolSamples {

    public static final String EXTERNAL_TOOLS_LIST_SAMPLE = """
            [
                {
                    "name": "NAME 1",
                    "description": "DESCRIPTION 1",
                    "versions": [
                        {
                            "version": "1.0",
                            "releaseNote": "RELEASE_NOTE 1",
                            "dataverseMinVersion": "6.0",
                            "manifestStoredResourceId": [
                                1
                            ]
                        }
                    ],
                    "imagesResourceId": [
                        2
                    ]
                }
            ]
            """;

    public static final String EXTERNAL_TOOL_SINGLE_SAMPLE = """
           {
                "name": "NAME",
                "description": "DESCRIPTION2",
                "versions": [
                    {
                        "version": "1.0",
                        "releaseNote": "RELEASE_NOTE",
                        "dataverseMinVersion": "6.0",
                        "manifestStoredResourceId": [
                            19
                        ]
                    }
                ],
                "images": [
                    20
                ]
            }
            """;

    public static final String EXTERNAL_TOOL_VERSIONS_LIST_SAMPLE = """
            [{
                "version": "1.0",
                "releaseNote": "RELEASE_NOTE",
                "dataverseMinVersion": "6.0",
                "manifestStoredResourceId": [
                    1
                ]
            }]
            """;

    public static final String EXTERNAL_TOOL_MULTIPART_FORM_SAMPLE = """
            {
                "name": "Ask the Data",
                "description": "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.",
                "releaseNote": "This release includes a new feature that allows you to ask questions to an LLM.",
                "version": "1.0",
                "dvMinVersion": "6.0",
                "jsonData": [
                    "ask-the-data.json"
                ],
                "itemImages": [
                    "ask-the-data.png"
                ]
            }
            """;

}
