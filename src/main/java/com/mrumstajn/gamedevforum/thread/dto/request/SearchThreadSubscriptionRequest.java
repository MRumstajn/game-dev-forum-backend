package com.mrumstajn.gamedevforum.thread.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchThreadSubscriptionRequest {
    private Long userId;

    private Long threadId;
}
