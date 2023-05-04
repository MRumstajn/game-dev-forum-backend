package com.mrumstajn.gamedevforum.workoffer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EditWorkOfferRequest {
    @Size(min = 3, max = 30)
    private String title;

    @Size(min = 3, max = 200)
    private String description;

    @Min(0)
    private BigDecimal pricePerHour;
}
