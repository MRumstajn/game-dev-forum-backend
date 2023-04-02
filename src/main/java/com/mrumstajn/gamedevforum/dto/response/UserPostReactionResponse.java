package com.mrumstajn.gamedevforum.dto.response;

import com.mrumstajn.gamedevforum.entity.PostReactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostReactionResponse {
    private Long userId;

    private Long postId;

    private PostReactionType postReactionType;
}
