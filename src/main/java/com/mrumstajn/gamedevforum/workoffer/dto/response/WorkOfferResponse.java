package com.mrumstajn.gamedevforum.workoffer.dto.response;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WorkOfferResponse {
    private Long id;

    private String title;

    private String description;

    private BigDecimal pricePerHour;

    private ForumUserResponse author;

    private Character currencySymbol;

    private Long workOfferCategoryId;
}
