package org.dataverse.marketplace.model;

import java.io.Serializable;
import java.util.Set;

import org.dataverse.marketplace.model.enums.HttpMethod;
import org.dataverse.marketplace.model.enums.Scope;

import jakarta.persistence.*;

@Entity
public class ExternalToolVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "external_tool_id")
    private ExternalTool externalTool;

    private String versionName;

    private String versionNote;

    private String dataverseMinVersion;

    // Manifest details
    private String displayName;

    private String description;

    @Enumerated(EnumType.STRING)
    private Scope scope;

    private String toolUrl;

    private String toolName;
    
    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QueryParameter> queryParameters;    

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExternalToolType> externalToolTypes;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContentType> contentTypes;
    
    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AllowedApiCall> allowedApiCalls;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuxFilesExist> auxFilesExist;


    /* Getters and Setters */

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalTool getExternalTool() {
        return this.externalTool;
    }

    public void setExternalTool(ExternalTool externalTool) {
        this.externalTool = externalTool;
    }

    public String getVersionName() {
        return versionName;
    }
    
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionNote() {
        return versionNote;
    }

    public void setVersionNote(String versionNote) {
        this.versionNote = versionNote;
    }

    public String getDataverseMinVersion() {
        return dataverseMinVersion;
    }

    public void setDataverseMinVersion(String dataverseMinVersion) {
        this.dataverseMinVersion = dataverseMinVersion;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Scope getScope() {
        return scope;
    }
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getToolUrl() {
        return toolUrl;
    }
    
    public void setToolUrl(String toolUrl) {
        this.toolUrl = toolUrl;
    }

    public String getToolName() {
        return toolName;
    }
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Set<ExternalToolType> getExternalToolTypes() {
        return externalToolTypes;
    }
    public void setExternalToolTypes(Set<ExternalToolType> externalToolTypes) {
        this.externalToolTypes = externalToolTypes;
    }

    public Set<QueryParameter> getQueryParameters() {
        return queryParameters;
    }
    public void setQueryParameters(Set<QueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }
    public Set<ContentType> getContentTypes() {
        return contentTypes;
    }
    public void setContentTypes(Set<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Set<AllowedApiCall> getAllowedApiCalls() {
        return allowedApiCalls;
    }
    public void setAllowedApiCalls(Set<AllowedApiCall> allowedApiCalls) {
        this.allowedApiCalls = allowedApiCalls;
    }

    public Set<AuxFilesExist> getAuxFilesExist() {
        return auxFilesExist;
    }
    public void setAuxFilesExist(Set<AuxFilesExist> auxFilesExist) {
        this.auxFilesExist = auxFilesExist;
    }
}
