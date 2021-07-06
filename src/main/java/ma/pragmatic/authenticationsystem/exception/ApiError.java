package ma.pragmatic.authenticationsystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // does not show nullable attributes
public class ApiError {
    private int status;
    private Timestamp timestamp;
    private String message;
    private String path;
    private Map<String, String> validationErrors;

    public ApiError() {
    }

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
