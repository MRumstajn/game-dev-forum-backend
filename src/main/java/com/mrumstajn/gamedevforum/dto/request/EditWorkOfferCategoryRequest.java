package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditWorkOfferCategoryRequest {
    @Size(min = 3, max = 30)
    private String title;

    @Size(min = 3, max = 200)
    private String description;
}
