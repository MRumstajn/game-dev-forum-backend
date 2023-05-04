package com.mrumstajn.gamedevforum.workoffer.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkOfferAverageAndTotalRatingRequest {
    @NotEmpty
    private List<Long> workOfferIds;
}
