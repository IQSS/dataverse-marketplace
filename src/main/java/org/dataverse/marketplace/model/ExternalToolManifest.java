package org.dataverse.marketplace.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "external_tool_manifest")
public class ExternalToolManifest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @ManyToOne
    @JoinColumn(name = "version_id")
    private ExternalToolVersion externalToolVersion;


    // Manifest details
    private String displayName;

    private String description;

    private String scope;

    private String toolUrl;

    private String toolName;
    
    private String httpMethod;

    @OneToMany(mappedBy = "manifest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QueryParameter> queryParameters;    

    @OneToMany(mappedBy = "manifest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExternalToolType> externalToolTypes;

    @OneToMany(mappedBy = "manifest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContentType> contentTypes;      

    //allow api calls
    //requirements


    /* Getters and Setters */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return this.externalToolVersion;
    }

    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
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

    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
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
    public String getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod) {
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

}
