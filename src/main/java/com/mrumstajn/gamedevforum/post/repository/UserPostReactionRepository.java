package com.mrumstajn.gamedevforum.post.repository;

import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import com.mrumstajn.gamedevforum.post.entity.UserPostReaction;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostReactionRepository extends JpaRepository<UserPostReaction, Long>, SearchExecutor<UserPostReaction> {
    Long countAllByPostReactionTypeAndPostId(PostReactionType postReactionType, Long postId);

    void deleteAllByPostId(Long postId);
}
