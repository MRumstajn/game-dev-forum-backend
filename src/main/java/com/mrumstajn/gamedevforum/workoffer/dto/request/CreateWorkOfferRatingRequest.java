package com.mrumstajn.gamedevforum.workoffer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class CreateWorkOfferRatingRequest {
    @JsonProperty("workOfferId")
    private Long workOfferIdentifier;

    @NotNull
    @Range(min = 1, max = 5)
    private Integer rating;
}
