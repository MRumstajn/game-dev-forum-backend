package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private Long id;

    private ForumUserResponse author;

    private String content;

    private LocalDateTime creationDateTime;

    private Long threadId;

    private Long likes;

    private Long dislikes;
}
