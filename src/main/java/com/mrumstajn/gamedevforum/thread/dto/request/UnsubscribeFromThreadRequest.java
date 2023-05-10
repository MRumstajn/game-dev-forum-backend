package com.mrumstajn.gamedevforum.thread.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnsubscribeFromThreadRequest {
    @NotNull
    private Long threadId;
}