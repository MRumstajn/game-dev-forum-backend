package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.entity.UserFollower;

import java.util.List;

public interface UserFollowerQueryService {
    List<ForumUser> getFollowersByFollowedUserId(Long followedUserId);

    UserFollower getByFollowerIdAndFollowedUserId(Long followerId, Long followedUserId);

    Boolean isUserFollowingUser(Long targetUserId);
}
