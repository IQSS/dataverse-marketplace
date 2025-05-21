package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.Objects;

import org.dataverse.marketplace.model.AllowedApiCall;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.enums.HttpMethod;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "AllowedApiCall",
    description = "Allowed Api Call data")
public class AllowedApiCallDTO implements Serializable {

    String name;
    HttpMethod httpMethod;
    String urlTemplate;
    Integer timeOut;

    public AllowedApiCallDTO() {
    }

    public AllowedApiCallDTO(String name, HttpMethod httpMethod, String urlTemplate, Integer timeOut) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.urlTemplate = urlTemplate;
        this.timeOut = timeOut;
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

    @Override
    public String toString() {
        return "AllowedApiCallDTO{" +
                "name='" + name + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", urlTemplate='" + urlTemplate + '\'' +
                ", timeOut=" + timeOut +
                '}';
    }

    public static EntitySyncUtil.EntityBuilder<AllowedApiCallDTO, AllowedApiCall> allowedApiCallBuilder(ExternalToolVersion version) {
        return new EntitySyncUtil.EntityBuilder<>() {
            @Override
            public AllowedApiCall build(AllowedApiCallDTO allowedApiCallDTO) {
                AllowedApiCall allowedApiCall = new AllowedApiCall();
                allowedApiCall.setExternalToolVersion(version);
                allowedApiCall.setName(allowedApiCallDTO.getName());
                allowedApiCall.setHttpMethod(allowedApiCallDTO.getHttpMethod());
                allowedApiCall.setUrlTemplate(allowedApiCallDTO.getUrlTemplate());
                allowedApiCall.setTimeOut(allowedApiCallDTO.getTimeOut());
                return allowedApiCall;
            }

            @Override
            public boolean matches(AllowedApiCallDTO allowedApiCallDTO, AllowedApiCall allowedApiCall) {
                return Objects.equals(allowedApiCallDTO.getName(), allowedApiCall.getName()) &&
                       Objects.equals(allowedApiCallDTO.getHttpMethod(), allowedApiCall.getHttpMethod()) &&
                       Objects.equals(allowedApiCallDTO.getUrlTemplate(), allowedApiCall.getUrlTemplate()) &&
                       Objects.equals(allowedApiCallDTO.getTimeOut(), allowedApiCall.getTimeOut());
            }
        };
    }  

}
