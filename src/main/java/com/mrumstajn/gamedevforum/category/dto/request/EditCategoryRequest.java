package com.mrumstajn.gamedevforum.category.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCategoryRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private String title;
}
