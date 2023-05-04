package com.mrumstajn.gamedevforum.post.dto.request;

import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserPostReactionRequest {
    @NotNull
    private PostReactionType postReactionType;
}
