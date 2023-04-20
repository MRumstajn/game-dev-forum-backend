package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOfferAverageAndTotalRatingResponse {
    private Long workOfferId;

    private Integer averageRating;

    private Long totalRatings;
}
