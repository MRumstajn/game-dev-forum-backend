package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryStatisticRequest {
    private List<Long> categoryIds;
}
