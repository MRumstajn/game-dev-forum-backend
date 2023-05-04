package com.mrumstajn.gamedevforum.message.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.model.sort.SortDirection;
import net.croz.nrich.search.api.model.sort.SortProperty;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchMessagesRequestPageable extends BaseSortablePageableRequest {
    private Long authorId;

    private Long recipientId;

    private LocalDateTime creationDateTimeFromIncluding;

    private LocalDateTime creationDateTimeToIncluding;

    private Boolean isRead;

    private Long conversationId;

    public SearchMessagesRequestPageable(){
        setPageNumber(0);
        setPageSize(10);
        setSortPropertyList(List.of(new SortProperty("creationDateTime", SortDirection.ASC)));
    }
}
