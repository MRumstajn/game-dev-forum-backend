package com.mrumstajn.gamedevforum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrumstajn.gamedevforum.entity.PostReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserPostReactionRequest {
    @NotNull
    @JsonProperty("postId")
    private Long postIdentifier;

    @NotNull
    private PostReactionType postReactionType;
}
