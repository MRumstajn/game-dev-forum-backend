package com.mrumstajn.gamedevforum.statistic.dto.response;

import com.mrumstajn.gamedevforum.post.dto.response.PostResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadStatisticResponse {
    private Long threadId;

    private Long postCount;

    private PostResponse latestPost;
}
