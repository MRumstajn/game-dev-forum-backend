package com.mrumstajn.gamedevforum.thread.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateForumThreadRequest {
    @NotBlank
    private String title;

    @NotNull
    @JsonProperty("categoryId")
    private Long categoryIdentifier;

    @NotNull
    @Size(min = 3)
    private String firstPostContent;
}
