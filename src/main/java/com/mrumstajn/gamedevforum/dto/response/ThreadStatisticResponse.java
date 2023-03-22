package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadStatisticResponse {
    private Long threadId;

    private Long postCount;

    private PostResponse latestPost;
}
