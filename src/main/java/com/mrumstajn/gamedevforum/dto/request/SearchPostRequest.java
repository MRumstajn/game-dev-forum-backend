package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchPostRequest {
    private Long threadId;

    private Long authorId;

    private LocalDateTime creationDateTime;

    private Long likes;

    private Long dislikes;
}
