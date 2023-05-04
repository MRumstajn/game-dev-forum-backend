package com.mrumstajn.gamedevforum.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFollowerResponse {
    private Long id;

    private ForumUserResponse follower;

    private ForumUserResponse followedUser;
}
