package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateForumThreadRequest {
    @NotBlank
    private String title;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long authorId;
}
