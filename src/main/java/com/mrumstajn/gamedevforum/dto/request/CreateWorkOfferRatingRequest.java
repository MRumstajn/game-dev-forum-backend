package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class CreateWorkOfferRatingRequest {
    private Long workOfferId;

    @NotNull
    @Range(min = 1, max = 5)
    private Integer rating;
}
