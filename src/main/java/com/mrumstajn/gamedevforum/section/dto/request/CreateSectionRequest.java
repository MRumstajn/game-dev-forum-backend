package com.mrumstajn.gamedevforum.section.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSectionRequest {

    @NotBlank
    private String title;
}
