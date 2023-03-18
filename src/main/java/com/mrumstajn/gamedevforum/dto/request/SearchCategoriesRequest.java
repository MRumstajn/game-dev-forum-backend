package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCategoriesRequest {
    private Long sectionId;

    private String title;
}
