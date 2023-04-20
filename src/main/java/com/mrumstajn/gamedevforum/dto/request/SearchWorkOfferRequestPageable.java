package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.math.BigDecimal;

@Getter
@Setter
public class SearchWorkOfferRequestPageable extends BaseSortablePageableRequest {
    private String title;

    private BigDecimal pricePerHourFromIncluding;

    private BigDecimal pricePerHourToIncluding;

    private Integer averageRatingFromIncluding;

    private Integer averageRatingToIncluding;

    private String authorUsername;

    public SearchWorkOfferRequestPageable(){
        setPageNumber(0);
        setPageNumber(10);
    }
}
