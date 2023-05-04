package com.mrumstajn.gamedevforum.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckIsFollowingRequest {
    @NotNull
    private Long targetUserId;
}
