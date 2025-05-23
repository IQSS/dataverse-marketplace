package org.dataverse.marketplace.model;

import org.dataverse.marketplace.model.enums.HttpMethod;

import jakarta.persistence.*;

@Entity
public class AllowedApiCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    @Enumerated(EnumType.STRING)
    HttpMethod httpMethod;
    String urlTemplate;
    Integer timeOut;

    @ManyToOne
    @JoinColumn(name = "external_tool_version_id", nullable = false)
    private ExternalToolVersion externalToolVersion;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExternalToolVersion getExternalToolVersion() {
        return externalToolVersion;
    }

    public void setExternalToolVersion(ExternalToolVersion externalToolVersion) {
        this.externalToolVersion = externalToolVersion;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }
    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    public Integer getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

}
