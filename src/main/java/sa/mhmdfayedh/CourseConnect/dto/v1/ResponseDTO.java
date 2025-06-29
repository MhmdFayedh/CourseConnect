package sa.mhmdfayedh.CourseConnect.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    @Builder.Default
    private String timestamp = ZonedDateTime.now().toString();
    @Builder.Default
    private String status = "success";
    @Builder.Default
    private int statusCode = HttpStatus.OK.value();
    private String message;
    private List<T> data;
    private PaginationDTO pagination;
}
