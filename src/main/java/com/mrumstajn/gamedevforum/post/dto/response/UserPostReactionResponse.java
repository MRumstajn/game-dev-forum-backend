package com.mrumstajn.gamedevforum.post.dto.response;

import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPostReactionResponse {
    private Long id;

    private Long userId;

    private Long postId;

    private PostReactionType postReactionType;

    private List<PostReactionTypeCountResponse> reactionTypesCount;
}
