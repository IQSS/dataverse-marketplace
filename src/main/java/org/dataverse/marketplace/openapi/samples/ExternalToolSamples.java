package org.dataverse.marketplace.openapi.samples;

public abstract class ExternalToolSamples {

    public static final String EXTERNAL_TOOLS_LIST_SAMPLE = '['
            + ExternalToolSamples.EXTERNAL_TOOL_SINGLE_SAMPLE + ']';

    public static final String EXTERNAL_TOOL_SINGLE_SAMPLE = """
            {
                 "id": 2,
                 "name": "Ask the data",
                 "description": "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.",
                 "versions": [
                 {
                     "version": "1",
                     "id": 1,
                     "releaseNote": "This release includes a new feature that allows you to ask questions to an LLM.",
                     "dataverseMinVersion": "6",
                     "manifests": [
                     {
                         "manifestId": 1,
                         "storedResourceId": 4
                     },
                     {
                         "manifestId": 2,
                         "storedResourceId": 6
                     }
                     ]
                 }
                 ],
                 "images": [
                 5
                 ]
             }
             """;

    public static final String EXTERNAL_TOOL_MULTIPART_FORM_SAMPLE = """
            {
                "name": "Ask the Data",
                "description": "Ask the Data is an experimental tool that allows you ask natural language questions about the data contained in Dataverse.",
                "versionNote": "This release includes a new feature that allows you to ask questions to an LLM.",
                "versionName": "1.0",
                "dataverseMinVersion": "6.0",
                "manifest": {
                    "displayName": "Ask the Data",
                    "description": "A tool that allows you to ask questions about Dataverse tabular data.",
                    "toolName": "AskTheData",
                    "scope": "file",
                    "types": [
                        "query"
                    ],
                    "toolUrl": "https://askdataverse.shinyapps.io/askthedata",
                    "contentTypes": [
                        "text/tab-separated-values",
                        "application/pdf"
                    ],
                    "httpMethod":"GET",
                    "toolParameters": {
                        "queryParameters": [
                        {
                            "fileId": "{fileId}"
                        },
                        {
                            "datasetPid": "{datasetPid}"
                        },
                        {
                            "siteUrl": "{siteUrl}"
                        }
                        ]
                    }
                }
            }
            """;

    public static final String EXTERNAL_TOOL_IMAGE_SAMPLE_ARR = 
        "[" + ExternalToolSamples.EXTERNAL_TOOL_IMAGE_SAMPLE + ']';

    public static final String EXTERNAL_TOOL_IMAGE_SAMPLE = """
            {
                "imageId": 2,
                "storedResourceId": 5
            }
            """;
    public static final String UPDATE_EXTERNAL_TOOL_REQUEST = """
            {
                "name": "Ask the data",
                "description": "As the data is..."
            }
            """;


}
