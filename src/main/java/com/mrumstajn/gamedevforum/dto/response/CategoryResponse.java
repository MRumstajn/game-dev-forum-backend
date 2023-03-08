package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponse {
    private Long id;

    private Long sectionId;

    private String title;

    private List<ForumThreadResponse> threads;
}
