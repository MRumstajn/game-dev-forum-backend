package com.mrumstajn.gamedevforum.user.service.query;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.entity.UserFollower;

import java.util.List;

public interface UserFollowerQueryService {
    List<ForumUser> getFollowersByFollowedUserId(Long followedUserId);

    UserFollower getByFollowerIdAndFollowedUserId(Long followerId, Long followedUserId);

    Boolean isUserFollowingUser(Long targetUserId);
}
