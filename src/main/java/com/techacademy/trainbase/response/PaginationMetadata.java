package com.techacademy.trainbase.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationMetadata {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrevious;

    public static PaginationMetadata of(int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / Math.max(size, 1));
        return PaginationMetadata.builder()
            .page(page)
            .size(size)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .first(page == 0)
            .last(page >= totalPages - 1)
            .hasNext(page < totalPages - 1)
            .hasPrevious(page > 0)
            .build();
    }
}
