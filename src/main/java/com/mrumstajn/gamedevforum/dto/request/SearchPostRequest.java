package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchPostRequest {
    private Long threadId;

    private Long authorId;

    private LocalDate creationDate;

    private Long likes;

    private Long dislikes;
}
