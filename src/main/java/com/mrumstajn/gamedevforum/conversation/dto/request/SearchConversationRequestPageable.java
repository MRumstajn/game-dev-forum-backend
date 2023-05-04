package com.mrumstajn.gamedevforum.conversation.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

@Getter
@Setter
public class SearchConversationRequestPageable extends BaseSortablePageableRequest {
    public SearchConversationRequestPageable(){
        setPageNumber(0);
        setPageSize(10);
    }
}
