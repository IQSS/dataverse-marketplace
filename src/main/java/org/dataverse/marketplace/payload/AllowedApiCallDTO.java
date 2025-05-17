package org.dataverse.marketplace.payload;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Allowed Api Call data transfer object")
public class AllowedApiCallDTO implements Serializable {


    String name;
    String httpMethod;
    String urlTemplate;
    Integer timeOut;

    public AllowedApiCallDTO() {
    }
    
    public AllowedApiCallDTO(String name, String httpMethod, String urlTemplate, Integer timeOut) {
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

    public String getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod) {
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
