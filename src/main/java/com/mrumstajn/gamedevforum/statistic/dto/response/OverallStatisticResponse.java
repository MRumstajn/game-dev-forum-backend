package com.mrumstajn.gamedevforum.statistic.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverallStatisticResponse {
    private Long categoryCount;

    private Long threadCount;

    private Long postCount;

    private Long userCount;
}
