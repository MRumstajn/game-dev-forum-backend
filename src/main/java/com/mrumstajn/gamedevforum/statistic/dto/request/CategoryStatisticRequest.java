package com.mrumstajn.gamedevforum.statistic.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryStatisticRequest {
    @NotEmpty
    private List<Long> categoryIds;
}
