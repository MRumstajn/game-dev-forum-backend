package com.mrumstajn.gamedevforum.category.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {
    @NotNull
    @JsonProperty("sectionId")
    private Long sectionIdentifier;

    @NotBlank
    private String title;
}
