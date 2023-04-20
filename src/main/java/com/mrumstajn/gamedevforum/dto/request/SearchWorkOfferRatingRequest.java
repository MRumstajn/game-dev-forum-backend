package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchWorkOfferRatingRequest {
    private Long userId;

    private Long workOfferId;
}
