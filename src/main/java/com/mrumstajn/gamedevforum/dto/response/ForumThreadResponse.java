package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ForumThreadResponse {
    private Long id;

    private CategoryResponse category;

    private ForumUserResponse author;

    private LocalDateTime creationDateTime;

    private String title;

    private Boolean deleted;
}
