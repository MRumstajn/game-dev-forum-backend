package com.mrumstajn.gamedevforum.dto.response;

import com.mrumstajn.gamedevforum.entity.PostReactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReactionTypeCountResponse {
    private Long postId;

    private Long count;

    private PostReactionType postReactionType;
}
