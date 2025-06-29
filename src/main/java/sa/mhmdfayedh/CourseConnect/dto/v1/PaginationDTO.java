package sa.mhmdfayedh.CourseConnect.dto.v1;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDTO {
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;
    private boolean last;
    private boolean first;
}
