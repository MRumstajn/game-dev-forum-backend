package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchForumThreadRequest {
    private Long categoryId;

    private String title;

    private Long authorId;

    private String authorUsername;

    private LocalDateTime creationDateTimeFromIncluding;

    private LocalDateTime creationDateTimeToIncluding;
}
