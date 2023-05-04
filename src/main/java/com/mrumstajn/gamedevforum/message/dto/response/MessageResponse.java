package com.mrumstajn.gamedevforum.message.dto.response;

import com.mrumstajn.gamedevforum.conversation.dto.response.ConversationResponse;
import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {
    private Long id;

    private String content;

    private ConversationResponse conversation;

    private ForumUserResponse author;

    private LocalDateTime creationDateTime;

    private Boolean isRead;

    private Boolean deleted;
}
