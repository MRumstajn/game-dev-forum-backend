package com.mrumstajn.gamedevforum.statistic.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThreadStatisticRequest {
    @NotEmpty
    private List<Long> threadIds;
}
