package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
    List<UserFollower> findAllByFollowedUserId(Long followedUserId);

    Optional<UserFollower> findByFollowerIdAndFollowedUserId(Long followerId, Long followedUserId);

    Boolean existsByFollowerIdAndFollowedUserId(Long followerId, Long followedUserId);
}
