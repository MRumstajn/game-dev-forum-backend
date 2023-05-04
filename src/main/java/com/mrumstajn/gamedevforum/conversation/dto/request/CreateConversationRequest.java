package com.mrumstajn.gamedevforum.conversation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateConversationRequest {
    @NotNull
    private Long participantAId;

    @NotNull
    private Long participantBId;
}
