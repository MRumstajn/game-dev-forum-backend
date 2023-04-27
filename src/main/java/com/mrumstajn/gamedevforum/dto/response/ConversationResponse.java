package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ConversationResponse {
    private Long id;

    private List<ForumUserResponse> participants;

    private Long unreadMessages;

    private LocalDateTime latestMessageDateTime;
}
