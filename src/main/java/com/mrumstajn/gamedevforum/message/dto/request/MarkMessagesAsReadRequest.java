package com.mrumstajn.gamedevforum.message.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MarkMessagesAsReadRequest {
    @Size(min = 1)
    private List<Long> messageIds;

    private Long conversationId;
}
