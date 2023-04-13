package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadSubscriptionResponse {
    private Long id;

    private Long userId;

    private Long threadId;
}
