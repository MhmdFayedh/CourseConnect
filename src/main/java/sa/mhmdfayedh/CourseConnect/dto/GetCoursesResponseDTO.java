package sa.mhmdfayedh.CourseConnect.dto;

import java.util.List;

public class GetCoursesResponseDTO {
    private String status;
    private int statusCode;
    private String message;
    private List<CourseDTO> data;
    private int total;

    public GetCoursesResponseDTO(){
    }

    public GetCoursesResponseDTO(String status, int statusCode, String message, List<CourseDTO> data, int total) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.total = total;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
