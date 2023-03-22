package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryStatisticResponse {
    private Long categoryId;

    private Long threadCount;

    private ForumThreadResponse threadWithLatestActivity;
}
