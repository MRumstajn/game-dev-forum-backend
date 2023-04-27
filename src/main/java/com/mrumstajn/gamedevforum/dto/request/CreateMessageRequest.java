package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMessageRequest {
    @NotNull
    @Size(min = 3, max = 300)
    private String content;

    @NotNull
    private Long conversationId;
}
