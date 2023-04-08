package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.entity.UserFollower;

public interface UserFollowerCommandService {
    UserFollower create(Long followedUserId);

    void delete(Long followedUserId);
}
