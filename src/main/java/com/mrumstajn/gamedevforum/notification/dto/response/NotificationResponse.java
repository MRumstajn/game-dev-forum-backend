package com.mrumstajn.gamedevforum.notification.dto.response;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
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
