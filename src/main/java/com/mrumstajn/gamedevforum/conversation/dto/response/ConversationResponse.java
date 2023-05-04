package com.mrumstajn.gamedevforum.conversation.dto.response;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConversationResponse {
    private Long id;

    private ForumUser participantA;

    private ForumUser participantB;

    private Long unreadMessages;

    private LocalDateTime latestMessageDateTime;
}
