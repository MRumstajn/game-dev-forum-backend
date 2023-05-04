package com.mrumstajn.gamedevforum.category.dto.response;

import com.mrumstajn.gamedevforum.section.dto.response.SectionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long id;

    private SectionResponse section;

    private String title;
}
