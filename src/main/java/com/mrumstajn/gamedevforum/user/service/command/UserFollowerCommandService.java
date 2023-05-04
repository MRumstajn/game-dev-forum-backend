package com.mrumstajn.gamedevforum.user.service.command;

import com.mrumstajn.gamedevforum.user.entity.UserFollower;

public interface UserFollowerCommandService {
    UserFollower create(Long followedUserId);

    void delete(Long followedUserId);
}
