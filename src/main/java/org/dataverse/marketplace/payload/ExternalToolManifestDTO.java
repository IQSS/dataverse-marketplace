package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dataverse.marketplace.model.ContentType;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.ExternalToolType;
import org.dataverse.marketplace.model.QueryParameter;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "External tool manifest data transfer object")
public class ExternalToolManifestDTO implements Serializable {

    // Manifest details
    @Schema(description = "The manifest's display name", example = "My External Tool")
    private String displayName;

    @Schema(description = "The manifest's description", example = "This is a description for na installed external tool.")
    private String description;

    @Schema(description = "The manifest's scope", example = "Dataset")
    private String scope;

    @Schema(description = "The manifest's tool URL", example = "https://example.com/tool")
    private String toolUrl;

    @Schema(description = "A name of an external tool that is used to differentiate between external tools and also used in bundle.properties for localization in the Dataverse installation web interface. ", example = "AskTheData")
    private String toolName;

    @Schema(description = "The manifest's HTTP method", example = "GET")
    private String httpMethod;

    @Schema(description = "The content type for the file the tool will work with", example = "application/pdf")
    private String contentType;

    @Schema(description = "The content types for the file the tool will work with", example = "application/pdf")
    private Set<String> contentTypes;

    @Schema(description = "The tool types", example = "query")
    private Set<String> types;

    // @Schema(description = "the query paramters", example = "fileid: {fileId}")
    private ToolParameterDTO toolParameters;

    public ExternalToolManifestDTO() {
    }

    public ExternalToolManifestDTO(ExternalToolVersion externalToolVersion) {
        this.displayName = externalToolVersion.getDisplayName();
        this.description = externalToolVersion.getDescription();
        this.scope = externalToolVersion.getScope();
        this.toolUrl = externalToolVersion.getToolUrl();
        this.toolName = externalToolVersion.getToolName();
        this.httpMethod = externalToolVersion.getHttpMethod();

        if (externalToolVersion.getExternalToolTypes() != null) {
            this.types = new HashSet<String>();
            for (ExternalToolType type : externalToolVersion.getExternalToolTypes()) {
                this.types.add(type.getType());
            }
        }

        if (externalToolVersion.getContentTypes() != null) {
            this.contentTypes = new HashSet<String>();
            for (ContentType contentType : externalToolVersion.getContentTypes()) {
                this.contentTypes.add(contentType.getContentType());
            }
        }

        if (externalToolVersion.getQueryParameters() != null
                && !externalToolVersion.getQueryParameters().isEmpty()) {
            this.toolParameters = new ToolParameterDTO();
            Set<Map<String, String>> queryParameters = new HashSet<Map<String, String>>();
            for (QueryParameter qp : externalToolVersion.getQueryParameters()) {
                Map<String, String> qpMap = new java.util.HashMap<String, String>();
                qpMap.put(qp.getKey(), qp.getValue());
                queryParameters.add(qpMap);
            }
            toolParameters.setQueryParameters(queryParameters);
        }

    }

    public ExternalToolManifestDTO(ExternalToolManifestDTO manifestDTO, String contentType) {
            this.setDisplayName(manifestDTO.displayName);
            this.setDescription(manifestDTO.description);
            this.setScope(manifestDTO.scope);
            this.setToolUrl(manifestDTO.toolUrl);
            this.setToolName(manifestDTO.toolName);
            this.setHttpMethod(manifestDTO.httpMethod);

            // specific content type
            this.setContentType(contentType);

            this.setTypes(manifestDTO.types);
            this.setToolParameters(manifestDTO.toolParameters);
    }

    public void convertDTOtoEntity(ExternalToolVersion version) {

        version.setDisplayName(this.getDisplayName());
        version.setDescription(this.getDescription());
        version.setScope(this.getScope());
        version.setToolUrl(this.getToolUrl());
        version.setToolName(this.getToolName());
        version.setHttpMethod(this.getHttpMethod());

        Set<ContentType> existingContentTypes = version.getContentTypes();

        if (existingContentTypes != null) {
            existingContentTypes.clear();
        } else {
            existingContentTypes = new HashSet<>();
            version.setContentTypes(existingContentTypes);
        }
        if (this.getContentTypes() != null) {
            for (String contentType : this.getContentTypes()) {
                if (StringUtils.isNotBlank(contentType)) {
                    ContentType newContentType = new ContentType();
                    newContentType.setExternalToolVersion(version);
                    newContentType.setContentType(contentType);
                    existingContentTypes.add(newContentType);
                }
            }
        }
        // we are uploading a manifest that uses the single content type
        if (existingContentTypes.isEmpty() && StringUtils.isNotBlank(this.getContentType())) {
            ContentType newContentType = new ContentType();
            newContentType.setExternalToolVersion(version);
            newContentType.setContentType(this.getContentType());
            existingContentTypes.add(newContentType);   
        }
       
        Set<ExternalToolType> existingTypes = version.getExternalToolTypes();

        if (existingTypes != null) {
            existingTypes.clear();
        } else {
            existingTypes = new HashSet<>();
            version.setExternalToolTypes(existingTypes);
        }
        if (this.getTypes() != null) {
            for (String type : this.getTypes()) {
                ExternalToolType newType = new ExternalToolType();
                newType.setExternalToolVersion(version);
                newType.setType(type);
                existingTypes.add(newType);
            }
        }

        Set<QueryParameter> existingQueryParameters = version.getQueryParameters();

        if (existingQueryParameters != null) {
            existingQueryParameters.clear();  
        } else {
            existingQueryParameters = new HashSet<>();
            version.setQueryParameters(existingQueryParameters);
        }      
        if (this.getToolParameters() != null &&
                this.getToolParameters().getQueryParameters() != null
                && !this.getToolParameters().getQueryParameters().isEmpty()) {
            for (Map<String, String> qpMap : this.getToolParameters().getQueryParameters()) {
                QueryParameter qp = new QueryParameter();
                qp.setExternalToolVersion(version);
                qp.setKey(qpMap.keySet().iterator().next());
                qp.setValue(qpMap.get(qp.getKey()));
                existingQueryParameters.add(qp);
            }
        }
    }




    /* Getters and Setters */
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToolUrl() {
        return this.toolUrl;
    }

    public void setToolUrl(String toolUrl) {
        this.toolUrl = toolUrl;
    }

    public String getToolName() {
        return this.toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Set<String> getContentTypes() {
        return this.contentTypes;
    }

    public void setContentTypes(Set<String> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Set<String> getTypes() {
        return this.types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public ToolParameterDTO getToolParameters() {
        return this.toolParameters;
    }

    public void setToolParameters(ToolParameterDTO toolParameters) {
        this.toolParameters = toolParameters;
    }

    @Override
    public String toString() {
        return "{" +
                ", ctype='" + getContentType() + "'" +
                "}";
    }

    // Inner class
    public class ToolParameterDTO implements Serializable {
        private Set<Map<String, String>> queryParameters;

        public Set<Map<String, String>> getQueryParameters() {
            return queryParameters;
        }

        public void setQueryParameters(Set<Map<String, String>> queryParameters) {
            this.queryParameters = queryParameters;
        }
    }
}