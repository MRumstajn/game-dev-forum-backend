package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.model.sort.SortDirection;
import net.croz.nrich.search.api.model.sort.SortProperty;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchForumThreadRequestPageable extends BaseSortablePageableRequest {
    private Long categoryId;

    private String title;

    private Long authorId;

    private String authorUsername;

    private LocalDateTime creationDateTimeFromIncluding;

    private LocalDateTime creationDateTimeToIncluding;

    public SearchForumThreadRequestPageable(){
        setPageSize(10);
        setPageNumber(0);
        setSortPropertyList(List.of(new SortProperty("id", SortDirection.DESC)));
    }
}
