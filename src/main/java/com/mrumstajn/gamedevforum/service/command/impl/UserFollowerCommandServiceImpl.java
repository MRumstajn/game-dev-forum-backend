package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.entity.UserFollower;
import com.mrumstajn.gamedevforum.exception.CannotFollowSelfException;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.repository.UserFollowerRepository;
import com.mrumstajn.gamedevforum.service.command.UserFollowerCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.service.query.UserFollowerQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserFollowerCommandServiceImpl implements UserFollowerCommandService {
    private final UserFollowerRepository userFollowerRepository;

    private final UserFollowerQueryService userFollowerQueryService;

    private final ForumUserQueryService forumUserQueryService;

    @Override
    public UserFollower create(Long followedUserId) {
        ForumUser currentUser = UserUtil.getCurrentUser();

        if (userFollowerQueryService.isUserFollowingUser(followedUserId)){
            throw new DuplicateResourceException(
                    "User with id " + currentUser.getId() + " is already following user with id " + followedUserId);
        }

        if (Objects.equals(currentUser.getId(), followedUserId)){
            throw new CannotFollowSelfException("User cannot follow itself");
        }

        UserFollower follow = new UserFollower();
        follow.setFollower(currentUser);
        follow.setFollowedUser(currentUser);
        follow.setFollowedUser(forumUserQueryService.getById(followedUserId));

        return userFollowerRepository.save(follow);
    }

    @Override
    public void delete(Long followedUserId) {
        // check if user is following user
        UserFollower existingFollow = userFollowerQueryService
                .getByFollowerIdAndFollowedUserId(UserUtil.getCurrentUser().getId(), followedUserId);

        userFollowerRepository.delete(existingFollow);
    }
}
