package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private Long id;

    private ForumUserResponse recipient;

    private String title;

    private String content;

    private Boolean isRead;
}
