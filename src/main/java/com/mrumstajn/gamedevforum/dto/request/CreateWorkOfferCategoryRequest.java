package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkOfferCategoryRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String title;
}
