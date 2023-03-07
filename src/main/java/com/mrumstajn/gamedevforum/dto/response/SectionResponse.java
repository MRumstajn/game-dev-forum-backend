package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionResponse {

    private Long id;

    private String title;

    private List<CategoryResponse> categories;
}
