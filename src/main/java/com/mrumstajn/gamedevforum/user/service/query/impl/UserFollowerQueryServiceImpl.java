package com.mrumstajn.gamedevforum.user.service.query.impl;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.entity.UserFollower;
import com.mrumstajn.gamedevforum.user.repository.UserFollowerRepository;
import com.mrumstajn.gamedevforum.user.service.query.UserFollowerQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFollowerQueryServiceImpl implements UserFollowerQueryService {
    private final UserFollowerRepository userFollowerRepository;

    @Override
    public List<ForumUser> getFollowersByFollowedUserId(Long followedUserId) {
        return userFollowerRepository.findAllByFollowedUserId(followedUserId).stream().map(UserFollower::getFollower).toList();
    }

    @Override
    public UserFollower getByFollowerIdAndFollowedUserId(Long followerId, Long followedUserId) {
        return userFollowerRepository.findByFollowerIdAndFollowedUserId(followerId, followedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + followerId + " is not following user with id " + followedUserId));
    }

    @Override
    public Boolean isUserFollowingUser(Long targetUserId) {
        return userFollowerRepository.existsByFollowerIdAndFollowedUserId(UserUtil.getCurrentUser().getId(), targetUserId);
    }
}
