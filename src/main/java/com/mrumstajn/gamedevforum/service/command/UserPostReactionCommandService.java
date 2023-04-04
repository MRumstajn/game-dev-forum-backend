package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.entity.UserPostReaction;

public interface UserPostReactionCommandService {

    UserPostReaction create(CreateUserPostReactionRequest request);

    UserPostReaction edit(Long id, EditUserPostReactionRequest request);

    void delete(Long id);

    void deleteAllByPostId(Long postId);
}
