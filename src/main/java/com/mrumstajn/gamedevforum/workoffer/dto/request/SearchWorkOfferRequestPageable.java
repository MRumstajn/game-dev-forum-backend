package com.mrumstajn.gamedevforum.workoffer.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Getter
@Setter
public class SearchWorkOfferRequestPageable extends BaseSortablePageableRequest {
    private String title;

    private BigDecimal pricePerHourFromIncluding;

    private BigDecimal pricePerHourToIncluding;

    private Integer averageRatingFromIncluding;

    @Range(min = 1, max = 5)
    private Integer averageRatingToIncluding;

    private String authorUsername;

    private Long workOfferCategoryId;

    public SearchWorkOfferRequestPageable(){
        setPageNumber(0);
        setPageNumber(10);
    }
}
