package sa.mhmdfayedh.CourseConnect.dto.v1;

import java.util.List;

public class GetUsersResponseDTO {
    private String status;
    private int statusCode;
    private String message;
    private List<UserDTO> data;
    private PaginationDTO pagination;

    public GetUsersResponseDTO(){

    }

    public GetUsersResponseDTO(String status, int statusCode, String message, List<UserDTO> data, PaginationDTO pagination) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
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

    public List<UserDTO> getData() {
        return data;
    }

    public void setData(List<UserDTO> data) {
        this.data = data;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}
