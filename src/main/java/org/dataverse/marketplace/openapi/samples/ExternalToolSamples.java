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
                },
                {
                    "name": "NAME",
                    "description": "DESCRIPTION2",
                    "versions": [
                        {
                            "version": "1.0",
                            "releaseNote": "RELEASE_NOTE",
                            "dataverseMinVersion": "6.0",
                            "manifestStoredResourceId": [
                                3
                            ]
                        }
                    ],
                    "imagesResourceId": [
                        4
                    ]
                }
            ]
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

}
