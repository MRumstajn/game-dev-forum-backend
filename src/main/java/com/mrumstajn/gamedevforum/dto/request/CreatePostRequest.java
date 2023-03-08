package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {
    @NotNull
    private Long authorId;

    @NotNull
    private Long threadId;

    @NotBlank
    private String content;
}
