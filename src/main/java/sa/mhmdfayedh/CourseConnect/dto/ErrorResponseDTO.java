package sa.mhmdfayedh.CourseConnect.dto;

import java.time.ZonedDateTime;

public class ErrorResponseDTO {
    private String timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponseDTO(){

    }

    public ErrorResponseDTO(int status, String error, String message){
        this.timestamp = ZonedDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
