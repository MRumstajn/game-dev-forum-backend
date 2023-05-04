package com.mrumstajn.gamedevforum.post.dto.response;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import com.mrumstajn.gamedevforum.thread.dto.response.ForumThreadResponse;
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

    private ForumThreadResponse thread;

    private Boolean deleted;
}
