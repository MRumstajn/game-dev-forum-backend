package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

@Getter
@Setter
public class SearchNotificationRequest extends BaseSortablePageableRequest {
    private Long recipientId;

    private Boolean isRead;

    public SearchNotificationRequest(){
        setPageNumber(0);
        setPageSize(10);
        //setSortPropertyList(List.of(new SortProperty("isRead", SortDirection.DESC)));
    }
}
