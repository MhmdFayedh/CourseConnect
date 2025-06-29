package sa.mhmdfayedh.CourseConnect.dto.v1;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorResponseDTO {
    @Builder.Default
    private String timestamp = ZonedDateTime.now().toString();
    private int status;
    private String error;
    private String message;

}
