package com.mrumstajn.gamedevforum.post.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.model.sort.SortDirection;
import net.croz.nrich.search.api.model.sort.SortProperty;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchPostRequestPageable extends BaseSortablePageableRequest {
    private Long threadId;

    private Long authorId;

    private String authorUsername;

    private LocalDateTime creationDateTimeFromIncluding;

    private LocalDateTime creationDateTimeToIncluding;

    private Long likesFromIncluding;

    private Long dislikesFromIncluding;

    public SearchPostRequestPageable(){
        setPageSize(10);
        setPageNumber(0);
        setSortPropertyList(List.of(new SortProperty("creationDateTime", SortDirection.ASC)));
    }
}
