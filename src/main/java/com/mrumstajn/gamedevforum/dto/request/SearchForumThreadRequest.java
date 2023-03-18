package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchForumThreadRequest {
    private Long categoryId;

    private String title;

    private Long authorId;

    private LocalDate creationDate;
}
