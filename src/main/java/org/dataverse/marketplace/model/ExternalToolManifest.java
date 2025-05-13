package org.dataverse.marketplace.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "external_tool_manifest", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "version_id", "mkt_item_id", "manifest_id" })
})
public class ExternalToolManifest {

    
    @Column(name = "version_id")
    Long versionId;

    
    @Column(name = "mkt_item_id")
    private Long mkItemId;

    @Id
    @Column(name = "manifest_id")
    private Long manifestId;
    
    @Column(name = "manifest_stored_resource_id")
    private Long manifestStoredResourceId;

    @Column(name = "mime_type")
    private String mimeType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manifest_stored_resource_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StoredResource storedResource;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "version_id", referencedColumnName = "id", insertable = false, updatable = false),
        @JoinColumn(name = "mkt_item_id", referencedColumnName = "mkt_item_id", insertable = false, updatable = false)
    })
    private ExternalToolVersion externalToolVersion;


    // Manifest details
    @Column(name = "display_name")
    private String displayName;

    @Column(name = "description")
    private String description;

    @Column(name = "scope")//enum?
    private String scope;

    @Column(name = "tool_url")
    private String toolUrl;

    @Column(name = "tool_name")
    private String toolName;
    
    @Column(name = "http_method")
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


    public Long getVersionId() {
        return this.versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getMkItemId() {
        return this.mkItemId;
    }

    public void setMkItemId(Long mkItemId) {
        this.mkItemId = mkItemId;
    }

    public Long getManifestId() {
        return this.manifestId;
    }

    public void setManifestId(Long manifestId) {
        this.manifestId = manifestId;
    }

    public Long getManifestStoredResourceId() {
        return this.manifestStoredResourceId;
    }

    public void setManifestStoredResourceId(Long manifestStoredResourceId) {
        this.manifestStoredResourceId = manifestStoredResourceId;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return this.externalToolVersion;
    }

    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
    }

    public StoredResource getStoredResource() {
        return this.storedResource;
    }

    public void setStoredResource(StoredResource storedResource) {
        this.storedResource = storedResource;
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
