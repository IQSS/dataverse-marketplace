package org.dataverse.marketplace.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class ExternalToolVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "external_tool_id")
    private ExternalTool externalTool;

    private String releaseNote;

    @Column(name = "item_version")
    private String version;

    @Column(name = "dv_min_version")
    private String dataverseMinVersion;

    // Manifest details
    private String displayName;

    private String description;

    private String scope;

    private String toolUrl;

    private String toolName;
    
    private String httpMethod;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QueryParameter> queryParameters;    

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExternalToolType> externalToolTypes;

    @OneToMany(mappedBy = "externalToolVersion", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public ExternalTool getExternalTool() {
        return this.externalTool;
    }

    public void setExternalTool(ExternalTool externalTool) {
        this.externalTool = externalTool;
    }

    public String getReleaseNote() {
        return this.releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDataverseMinVersion() {
        return this.dataverseMinVersion;
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
