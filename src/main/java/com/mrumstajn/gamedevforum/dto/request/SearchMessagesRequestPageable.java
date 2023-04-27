package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.time.LocalDateTime;

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
    }
}
