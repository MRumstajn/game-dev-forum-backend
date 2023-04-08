package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckIsFollowingResponse {
    private Long userId;

    private Long targetUserId;

    private Boolean isFollowing;
}
