package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dataverse.marketplace.model.AllowedApiCall;
import org.dataverse.marketplace.model.AuxFilesExist;
import org.dataverse.marketplace.model.ContentType;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.ExternalToolType;
import org.dataverse.marketplace.model.QueryParameter;
import org.dataverse.marketplace.model.enums.HttpMethod;
import org.dataverse.marketplace.model.enums.Scope;
import org.dataverse.marketplace.model.enums.ToolType;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "ExternalToolManifest", description = "External tool manifest")
public class ExternalToolManifestDTO implements Serializable {

    // Manifest details
    @Schema(description = "The manifest's display name", example = "My External Tool")
    private String displayName;

    @Schema(description = "The manifest's description", example = "This is a description for na installed external tool.")
    private String description;

    @Schema(description = "The manifest's scope", example = "Dataset")
    private Scope scope;

    @Schema(description = "The manifest's tool URL", example = "https://example.com/tool")
    private String toolUrl;

    @Schema(description = "A name of an external tool that is used to differentiate between external tools and also used in bundle.properties for localization in the Dataverse installation web interface. ", example = "AskTheData")
    private String toolName;

    @Schema(description = "The manifest's HTTP method", example = "GET")
    private HttpMethod httpMethod;

    @Schema(description = "The content types for the file the tool will work with", example = "application/pdf")
    private Set<String> contentTypes;

    @Schema(description = "The tool types", example = "query")
    private Set<ToolType> types;

    // @Schema(description = "the query paramters (as part of toolParameters)",
    // example = "fileid: {fileId}")
    private ToolParameterDTO toolParameters;

    // @Schema(description = "the allowed api calls", example = "fileid: {fileId}")
    private Set<AllowedApiCallDTO> allowedApiCalls;

    // @Schema(description = "the aux file exists (as part of requirements)",
    // example = "fileid: {fileId}")
    private RequirementsDTO requirements;

    // legacy support
    @Schema(description = "The content type for the file the tool will work with", example = "application/pdf")
    private String contentType;

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
            this.types = new HashSet<ToolType>();
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

        if (externalToolVersion.getAllowedApiCalls() != null) {
            this.allowedApiCalls = new HashSet<AllowedApiCallDTO>();
            for (AllowedApiCall allowedApiCall : externalToolVersion.getAllowedApiCalls()) {
                AllowedApiCallDTO allowedApiCallDTO = new AllowedApiCallDTO();
                allowedApiCallDTO.setName(allowedApiCall.getName());
                allowedApiCallDTO.setHttpMethod(allowedApiCall.getHttpMethod());
                allowedApiCallDTO.setUrlTemplate(allowedApiCall.getUrlTemplate());
                allowedApiCallDTO.setTimeOut(allowedApiCall.getTimeOut());
                this.allowedApiCalls.add(allowedApiCallDTO);
            }
        }

        if (externalToolVersion.getAuxFilesExist() != null
                && !externalToolVersion.getAuxFilesExist().isEmpty()) {
            this.requirements = new RequirementsDTO();
            Set<AuxFilesExistDTO> auxFileExistsDTOs = new HashSet<AuxFilesExistDTO>();
            for (AuxFilesExist auxFileExists : externalToolVersion.getAuxFilesExist()) {
                AuxFilesExistDTO auxFileExistsDTO = new AuxFilesExistDTO();
                auxFileExistsDTO.setFormatTag(auxFileExists.getFormatTag());
                auxFileExistsDTO.setFormatVersion(auxFileExists.getFormatVersion());
                auxFileExistsDTOs.add(auxFileExistsDTO);
            }
            this.requirements.setAuxFilesExist(auxFileExistsDTOs);
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
        this.setAllowedApiCalls(manifestDTO.allowedApiCalls);
        this.setRequirements(manifestDTO.requirements);
    }

    public void convertDTOtoEntity(ExternalToolVersion version) {

        version.setDisplayName(this.getDisplayName());
        version.setDescription(this.getDescription());
        version.setScope(this.getScope());
        version.setToolUrl(this.getToolUrl());
        version.setToolName(this.getToolName());
        version.setHttpMethod(this.getHttpMethod());


        // for the collections, we use a util that compares all the DTOs  
        // and entities and keeps any entity that stays the same
        EntitySyncUtil.syncEntities(
                version.getContentTypes(),
                this.getContentTypes(),
                ExternalToolManifestDTO.contentTypeBuilder(version));

        // we are uploading a manifest that uses the single content type
        if (version.getContentTypes().isEmpty() && StringUtils.isNotBlank(this.getContentType())) {
            ContentType newContentType = new ContentType();
            newContentType.setExternalToolVersion(version);
            newContentType.setContentType(this.getContentType());
            version.getContentTypes().add(newContentType);
        }                
                
        EntitySyncUtil.syncEntities(
                version.getExternalToolTypes(),
                this.getTypes(),
                ExternalToolManifestDTO.ExternalToolTypeBuilder(version));
                
        EntitySyncUtil.syncEntities(
                version.getQueryParameters(),
                this.getToolParameters() != null ? this.getToolParameters().getQueryParameters() : null,
                ExternalToolManifestDTO.queryParameterBuilder(version));                

        EntitySyncUtil.syncEntities(
                version.getAllowedApiCalls(),
                this.getAllowedApiCalls(),
                AllowedApiCallDTO.allowedApiCallBuilder(version));

        EntitySyncUtil.syncEntities(
                version.getAuxFilesExist(),
                this.getRequirements() != null ? this.getRequirements().getAuxFilesExist() : null,
                AuxFilesExistDTO.auxFilesExistBuilder(version));
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

    public Scope getScope() {
        return this.scope;
    }

    public void setScope(Scope scope) {
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

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
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

    public Set<ToolType> getTypes() {
        return this.types;
    }

    public void setTypes(Set<ToolType> types) {
        this.types = types;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public ToolParameterDTO getToolParameters() {
        return this.toolParameters;
    }

    public void setToolParameters(ToolParameterDTO toolParameters) {
        this.toolParameters = toolParameters;
    }

    public Set<AllowedApiCallDTO> getAllowedApiCalls() {
        return this.allowedApiCalls;
    }

    public void setAllowedApiCalls(Set<AllowedApiCallDTO> allowedApiCalls) {
        this.allowedApiCalls = allowedApiCalls;
    }

    public RequirementsDTO getRequirements() {
        return this.requirements;
    }

    public void setRequirements(RequirementsDTO requirements) {
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExternalToolManifestDTO{");
        sb.append("displayName='").append(displayName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", toolUrl='").append(toolUrl).append('\'');
        sb.append(", toolName='").append(toolName).append('\'');
        sb.append(", httpMethod='").append(httpMethod).append('\'');
        sb.append(", contentType='").append(contentType).append('\'');
        sb.append(", contentTypes=").append(contentTypes);
        sb.append(", types=").append(types);
        sb.append(", toolParameters=").append(toolParameters);
        sb.append(", allowedApiCalls=").append(allowedApiCalls);
        sb.append(", requirements=").append(requirements);
        sb.append('}');
        return sb.toString();
    }

    // Inner class
    @Schema(name = "ToolParameter", description = "Parameters of the tool")
    public class ToolParameterDTO implements Serializable {
        private Set<Map<String, String>> queryParameters;

        public Set<Map<String, String>> getQueryParameters() {
            return queryParameters;
        }

        public void setQueryParameters(Set<Map<String, String>> queryParameters) {
            this.queryParameters = queryParameters;
        }

        @Override
        public String toString() {
            return "ToolParameterDTO{" +
                    "queryParameters=" + queryParameters +
                    '}';
        }

    }

    // Inner class
    @Schema(name = "Requirements", description = "Requirements of the tool")
    public class RequirementsDTO implements Serializable {
        private Set<AuxFilesExistDTO> auxFilesExist;

        public Set<AuxFilesExistDTO> getAuxFilesExist() {
            return auxFilesExist;
        }

        public void setAuxFilesExist(Set<AuxFilesExistDTO> auxFilesExist) {
            this.auxFilesExist = auxFilesExist;
        }

        @Override
        public String toString() {
            return "RequirementsDTO{" +
                    "auxFilesExist=" + auxFilesExist +
                    '}';
        }

    }

    public static boolean isValidManifest(ExternalToolManifestDTO manifestDTO) {
        return !StringUtils.isBlank(manifestDTO.getToolUrl());
    }

    public static EntitySyncUtil.EntityBuilder<String, ContentType> contentTypeBuilder(ExternalToolVersion version) {
        return new EntitySyncUtil.EntityBuilder<>() {
            @Override
            public ContentType build(String contentTypeDTO) {
                ContentType contentType = new ContentType();
                contentType.setExternalToolVersion(version);               
                contentType.setContentType(contentTypeDTO);
                return contentType;
            }

            @Override
            public boolean matches(String contentTypeDTO, ContentType contentType) {
                return contentTypeDTO.equals(contentType.getContentType());
            }
        };
    }

    public static EntitySyncUtil.EntityBuilder<ToolType, ExternalToolType> ExternalToolTypeBuilder(
            ExternalToolVersion version) {
        return new EntitySyncUtil.EntityBuilder<>() {
            @Override
            public ExternalToolType build(ToolType externalToolTypeDTO) {
                ExternalToolType externalToolType = new ExternalToolType();
                externalToolType.setExternalToolVersion(version);
                externalToolType.setType(externalToolTypeDTO);
                return externalToolType;
            }

            @Override
            public boolean matches(ToolType externalToolTypeDTO, ExternalToolType externalToolType) {
                return externalToolTypeDTO == externalToolType.getType();
            }
        };
    }

    public static EntitySyncUtil.EntityBuilder<Map<String, String>, QueryParameter> queryParameterBuilder(
            ExternalToolVersion version) {
        return new EntitySyncUtil.EntityBuilder<>() {
            @Override
            public QueryParameter build(Map<String, String> queryParameterDTO) {
                QueryParameter queryParameter = new QueryParameter();
                String key = queryParameterDTO.keySet().iterator().next();
                queryParameter.setExternalToolVersion(version);
                queryParameter.setKey(key);
                queryParameter.setValue(queryParameterDTO.get(key));
                return queryParameter;
            }

            @Override
            public boolean matches(Map<String, String> queryParameterDTO, QueryParameter queryParameter) {
                String key = queryParameterDTO.keySet().iterator().next();
                return key.equals(queryParameter.getKey())
                        && queryParameterDTO.get(key).equals(queryParameter.getValue());
            }

        };
    }
}