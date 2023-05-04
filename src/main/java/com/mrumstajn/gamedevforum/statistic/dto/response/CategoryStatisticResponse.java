package com.mrumstajn.gamedevforum.statistic.dto.response;

import com.mrumstajn.gamedevforum.thread.dto.response.ForumThreadResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryStatisticResponse {
    private Long categoryId;

    private Long threadCount;

    private ForumThreadResponse threadWithLatestActivity;
}
