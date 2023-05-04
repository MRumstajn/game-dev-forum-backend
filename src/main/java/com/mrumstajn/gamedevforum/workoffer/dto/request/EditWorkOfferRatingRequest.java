package com.mrumstajn.gamedevforum.workoffer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class EditWorkOfferRatingRequest {
    @Range(min = 1, max = 5)
    private Integer rating;
}
