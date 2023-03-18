package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostResponse {
    private Long id;

    private ForumUserResponse author;

    private String content;

    private LocalDate creationDate;

    private Long threadId;

    private Long likes;

    private Long dislikes;
}
