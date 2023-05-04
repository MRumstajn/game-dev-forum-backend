package com.mrumstajn.gamedevforum.thread.dto.response;

import com.mrumstajn.gamedevforum.category.dto.response.CategoryResponse;
import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
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
