package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.dataverse.marketplace.model.ContentType;
import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolType;
import org.dataverse.marketplace.model.QueryParameter;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "External tool manifest data transfer object")
public class ExternalToolManifestDTO implements Serializable {

    @Schema(description = "The manifest ID", example = "1")
    public Long manifestId;

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

    public ExternalToolManifestDTO(ExternalToolManifest externalToolManifest) {
        this.manifestId = externalToolManifest.getId();
        this.displayName = externalToolManifest.getDisplayName();
        this.description = externalToolManifest.getDescription();
        this.scope = externalToolManifest.getScope();
        this.toolUrl = externalToolManifest.getToolUrl();
        this.toolName = externalToolManifest.getToolName();
        this.httpMethod = externalToolManifest.getHttpMethod();

        if (externalToolManifest.getExternalToolTypes() != null) {
            this.types = new HashSet<String>();
            for (ExternalToolType type : externalToolManifest.getExternalToolTypes()) {
                this.types.add(type.getType());
            }
        }

        if (externalToolManifest.getContentTypes() != null) {
            this.contentTypes = new HashSet<String>();
            for (ContentType contentType : externalToolManifest.getContentTypes()) {
                this.contentTypes.add(contentType.getContentType());
            }
        }

        if (externalToolManifest.getQueryParameters() != null
                && !externalToolManifest.getQueryParameters().isEmpty()) {
            this.toolParameters = new ToolParameterDTO();
            Set<Map<String, String>> queryParameters = new HashSet<Map<String, String>>();
            for (QueryParameter qp : externalToolManifest.getQueryParameters()) {
                Map<String, String> qpMap = new java.util.HashMap<String, String>();
                qpMap.put(qp.getKey(), qp.getValue());
                queryParameters.add(qpMap);
            }
            toolParameters.setQueryParameters(queryParameters);
        }

        /*
         * if(externalToolManifest.getQueryParameters() != null){
         * this.toolParameters = new ToolParameterDTO();
         * Set<QueryParameterDTO> queryParameterDTOs = new HashSet<QueryParameterDTO>();
         * for(QueryParameter qp : externalToolManifest.getQueryParameters()){
         * queryParameterDTOs.add(new QueryParameterDTO(qp));
         * }
         * toolParameters.setQueryParameters(queryParameterDTOs);
         * 
         * }
         */

    }

    /* Getters and Setters */

    public Long getManifestId() {
        return this.manifestId;
    }

    public void setManifestId(Long manifestId) {
        this.manifestId = manifestId;
    }

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
                " manifestId='" + getManifestId() + "'" +
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

        /*
         * private Set<QueryParameterDTO> queryParameters;
         * 
         * public Set<QueryParameterDTO> getQueryParameters() {
         * return queryParameters;
         * }
         * 
         * public void setQueryParameters(Set<QueryParameterDTO> queryParameters) {
         * this.queryParameters = queryParameters;
         * }
         */
    }

    public Set<ExternalToolManifestDTO> getManifestSet() {
        if (this.contentTypes != null) {

            Set<ExternalToolManifestDTO> manifestDTOs = new HashSet<>();
            for (String contentType : this.contentTypes) {
                ExternalToolManifestDTO manifestDTO = new ExternalToolManifestDTO();
                manifestDTO.setDisplayName(this.displayName);
                manifestDTO.setDescription(this.description);
                manifestDTO.setScope(this.scope);
                manifestDTO.setToolUrl(this.toolUrl);
                manifestDTO.setToolName(this.toolName);
                manifestDTO.setHttpMethod(this.httpMethod);

                // from the loop
                manifestDTO.setContentType(contentType);

                manifestDTO.setTypes(this.types);
                manifestDTO.setToolParameters(this.toolParameters);

                manifestDTOs.add(manifestDTO);
            }

            return manifestDTOs;
        }
        return null;
    }

}