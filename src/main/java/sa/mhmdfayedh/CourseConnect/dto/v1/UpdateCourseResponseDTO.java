package sa.mhmdfayedh.CourseConnect.dto.v1;

import java.util.List;

public class UpdateCourseResponseDTO {
    private String status;
    private int statusCode;
    private String message;
    private List<CourseDTO> data;

    public UpdateCourseResponseDTO(){

    }

    public UpdateCourseResponseDTO(String status, int statusCode, String message, List<CourseDTO> data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
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

    public List<CourseDTO> getData() {
        return data;
    }

    public void setData(List<CourseDTO> data) {
        this.data = data;
    }
}
