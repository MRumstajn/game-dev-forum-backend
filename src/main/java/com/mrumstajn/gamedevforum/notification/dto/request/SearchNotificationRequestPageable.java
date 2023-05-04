package com.mrumstajn.gamedevforum.notification.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.model.sort.SortDirection;
import net.croz.nrich.search.api.model.sort.SortProperty;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.util.List;

@Getter
@Setter
public class SearchNotificationRequestPageable extends BaseSortablePageableRequest {
    private Long recipientId;

    private Boolean isRead;

    public SearchNotificationRequestPageable(){
        setPageNumber(0);
        setPageSize(10);
        setSortPropertyList(List.of(new SortProperty("creationDate", SortDirection.DESC)));
    }
}
