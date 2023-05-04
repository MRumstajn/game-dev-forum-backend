package com.mrumstajn.gamedevforum.message.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditMessageRequest {
    @NotNull
    @Size(min = 3, max = 300)
    private String content;
}
