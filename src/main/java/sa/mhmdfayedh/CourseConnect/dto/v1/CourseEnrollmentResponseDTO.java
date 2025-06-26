package sa.mhmdfayedh.CourseConnect.dto.v1;

public class CourseEnrollmentResponseDTO {
    private String status;
    private int statusCode;
    private String message;

    public CourseEnrollmentResponseDTO(){

    }

    public CourseEnrollmentResponseDTO(String status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
