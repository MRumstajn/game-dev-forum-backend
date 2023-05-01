package com.mrumstajn.gamedevforum.dto.response;

import com.mrumstajn.gamedevforum.entity.ForumUser;
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
