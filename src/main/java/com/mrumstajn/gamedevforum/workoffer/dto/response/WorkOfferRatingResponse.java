package com.mrumstajn.gamedevforum.workoffer.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOfferRatingResponse {
    private Long id;

    private Long workOfferId;

    private Integer rating;

    private Long userId;
}
