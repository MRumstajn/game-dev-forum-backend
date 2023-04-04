package com.mrumstajn.gamedevforum.dto.request;

import com.mrumstajn.gamedevforum.entity.PostReactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchUserPostReactionRequest {
    private Long userId;

    private List<Long> postIds;

    private PostReactionType postReactionType;
}
