package com.mrumstajn.gamedevforum.workoffer.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

@Getter
@Setter
public class SearchWorkOfferCategoryRequestPageable extends BaseSortablePageableRequest {
    private String title;

    public SearchWorkOfferCategoryRequestPageable(){
        setPageNumber(0);
        setPageSize(5);
    }
}
