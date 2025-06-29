package sa.mhmdfayedh.CourseConnect.common.utils;

import sa.mhmdfayedh.CourseConnect.dto.v1.PaginationDTO;

import java.util.List;

public class PaginationUtils {
    public static PaginationDTO createPagination(List<?> currentPageItems, int totalElements, int pageNumber, int pageSize) {
        int totalPages = totalElements == 0 ? 1 : (totalElements + pageSize - 1) / pageSize;

        return PaginationDTO
                .builder()
                .size(currentPageItems.size())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .page(pageNumber)
                .first(pageNumber == 1)
                .last(pageNumber == totalPages)
                .build();
    }
}
