package org.dataverse.marketplace.payload;

import java.io.Serializable;

import org.dataverse.marketplace.model.ExternalToolType;

import com.fasterxml.jackson.annotation.JsonValue;

public class ExternalToolTypeDTO implements Serializable{
        private Integer id;
        private String type;
    
        public ExternalToolTypeDTO() {
        }
    
        public ExternalToolTypeDTO(ExternalToolType type) {
            this.id = type.getId();
            this.type = type.getType();
        }
        
        public ExternalToolTypeDTO(String type) {
            this.type = type;
        }
            
        // getters and setters
        public Integer getId() {
            return id;
        }
    
        public void setId(Integer id) {
            this.id = id;
        }
    
        @JsonValue
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
    
    }
    
