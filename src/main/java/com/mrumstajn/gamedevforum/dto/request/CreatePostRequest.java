package com.mrumstajn.gamedevforum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {
    @NotNull
    @JsonProperty("threadId")
    private Long threadIdentifier;

    @NotBlank
    @Size(min = 3, max = 200)
    private String content;
}
