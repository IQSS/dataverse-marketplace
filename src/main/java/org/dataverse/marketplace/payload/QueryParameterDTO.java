package org.dataverse.marketplace.payload;

import java.io.Serializable;

import org.dataverse.marketplace.model.QueryParameter;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class QueryParameterDTO implements Serializable {

    //private Integer id;
    private String key;
    private String value;

    public QueryParameterDTO() {
    }

    public QueryParameterDTO(QueryParameter queryParameter) {
        //this.id = queryParameter.getId();
        this.key = queryParameter.getKey();
        this.value = queryParameter.getValue();
    }
    
    @JsonAnySetter 
    public void add(String key, String value){ 
        this.setKey(key);
        this.setValue(value); 
    } 

/* 
    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        */

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
