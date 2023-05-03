package com.mrumstajn.gamedevforum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateWorkOfferRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String title;

    @NotNull
    @Size(min = 3, max = 200)
    private String description;

    @NotNull
    @Min(0)
    private BigDecimal pricePerHour;

    @NotNull
    private Character currencySymbol;

    @NotNull
    @JsonProperty("workOfferCategoryId")
    private Long workOfferCategoryIdentifier;
}
