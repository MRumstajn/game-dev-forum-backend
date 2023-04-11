package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long id;

    private SectionResponse section;

    private String title;
}
