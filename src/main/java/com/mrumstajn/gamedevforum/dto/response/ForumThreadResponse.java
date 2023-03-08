package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ForumThreadResponse {
    private Long id;

    private Long categoryId;

    private ForumUserResponse author;

    private LocalDate creationDate;

    private String title;

    private List<PostResponse> posts;
}
