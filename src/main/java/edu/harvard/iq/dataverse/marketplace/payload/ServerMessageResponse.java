package edu.harvard.iq.dataverse.marketplace.payload;

import java.util.Date;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A representation of the server message response")
public class ServerMessageResponse {
    
    @Schema(description = "Timestamp of the error", 
                example = "2024-10-31T20:13:03.422+00:00")
    private Date timestamp;

    @Schema(description = "HTTP status code", 
            example = "500")
    private int code;

    @Schema(description = "HTTP status message", 
            example = "Internal Server Error")
    private String status;

    @Schema(description = "Error message", 
            example = "Error message sample")
    private String message;

    @Schema(description = "Stack trace", 
            example = "edu.harvard.iq.dataverse.marketplace.controller.api.IndexController.test(IndexController.java:42)")
    private String details;

    public ServerMessageResponse(HttpStatus status, String message, String details) {
        this.timestamp = new Date();
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.details = details;
    }

    public ServerMessageResponse(HttpStatus status, Exception e) {
        this.timestamp = new Date();
        this.code = status.value();
        this.status = status.getReasonPhrase();
        this.message = e.getMessage();
        this.details = e.getStackTrace()[0].toString();
    }

    public ServerMessageResponse() {
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "{" +
            " timestamp='" + getTimestamp() + "'" +
            ", code='" + getCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", message='" + getMessage() + "'" +
            ", details='" + getDetails() + "'"+
            "}";
    }
        
}
