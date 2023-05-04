package com.mrumstajn.gamedevforum.post.dto.response;

import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReactionTypeCountResponse {
    private Long postId;

    private Long count;

    private PostReactionType postReactionType;
}
