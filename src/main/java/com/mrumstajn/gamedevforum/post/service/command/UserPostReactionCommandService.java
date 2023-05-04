package com.mrumstajn.gamedevforum.post.service.command;

import com.mrumstajn.gamedevforum.post.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.entity.UserPostReaction;

public interface UserPostReactionCommandService {

    UserPostReaction create(CreateUserPostReactionRequest request);

    UserPostReaction edit(Long id, EditUserPostReactionRequest request);

    void delete(Long id);

    void deleteAllByPostId(Long postId);
}
