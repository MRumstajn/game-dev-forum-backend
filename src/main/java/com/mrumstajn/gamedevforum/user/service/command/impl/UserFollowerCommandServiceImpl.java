package com.mrumstajn.gamedevforum.user.service.command.impl;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.entity.UserFollower;
import com.mrumstajn.gamedevforum.exception.CannotTargetSelfException;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.user.repository.UserFollowerRepository;
import com.mrumstajn.gamedevforum.user.service.command.UserFollowerCommandService;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.user.service.query.UserFollowerQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserFollowerCommandServiceImpl implements UserFollowerCommandService {
    private final UserFollowerRepository userFollowerRepository;

    private final UserFollowerQueryService userFollowerQueryService;

    private final ForumUserQueryService forumUserQueryService;

    @Override
    @Transactional
    public UserFollower create(Long followedUserId) {
        ForumUser currentUser = UserUtil.getCurrentUser();

        if (userFollowerQueryService.isUserFollowingUser(followedUserId)){
            throw new DuplicateResourceException(
                    "User with id " + currentUser.getId() + " is already following user with id " + followedUserId);
        }

        if (Objects.equals(currentUser.getId(), followedUserId)){
            throw new CannotTargetSelfException("User cannot follow itself");
        }

        UserFollower follow = new UserFollower();
        follow.setFollower(currentUser);
        follow.setFollowedUser(currentUser);
        follow.setFollowedUser(forumUserQueryService.getById(followedUserId));

        return userFollowerRepository.save(follow);
    }

    @Override
    @Transactional
    public void delete(Long followedUserId) {
        // check if user is following user
        UserFollower existingFollow = userFollowerQueryService
                .getByFollowerIdAndFollowedUserId(UserUtil.getCurrentUser().getId(), followedUserId);

        userFollowerRepository.delete(existingFollow);
    }
}
