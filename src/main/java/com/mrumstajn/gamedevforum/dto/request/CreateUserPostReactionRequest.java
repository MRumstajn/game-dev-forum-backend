package com.mrumstajn.gamedevforum.dto.request;

import com.mrumstajn.gamedevforum.entity.PostReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserPostReactionRequest {
    @NotNull
    private Long postId;

    @NotNull
    private PostReactionType postReactionType;
}
