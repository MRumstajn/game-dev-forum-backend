package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.exception.DuplicateReactionException;
import com.mrumstajn.gamedevforum.repository.UserPostReactionRepository;
import com.mrumstajn.gamedevforum.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.service.query.UserPostReactionQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostReactionCommandServiceImpl implements UserPostReactionCommandService {
    private final UserPostReactionRepository userPostReactionRepository;

    private final UserPostReactionQueryService reactionQueryService;

    private final PostQueryService postQueryService;

    private final ModelMapper modelMapper;

    @Override
    public UserPostReaction create(CreateUserPostReactionRequest request) {
        postQueryService.getById(request.getPostId()); // check if post exists first

        // check if same reaction already exists for the post and the current user
        SearchUserPostReactionRequest userPostReactionRequest = new SearchUserPostReactionRequest();
        userPostReactionRequest.setUserId(UserUtil.getCurrentUser().getId());
        userPostReactionRequest.setPostId(request.getPostId());

        UserPostReaction existingReaction = reactionQueryService.search(userPostReactionRequest);
        if (existingReaction != null) {
            // if reaction of same type exists, throw error
            if (existingReaction.getPostReactionType().name().equals(request.getPostReactionType().name())) {
                throw new DuplicateReactionException("The specified reaction has already been applied to the post");
            } else {
                // if reaction of different type exists, update the type to the specified type
                EditUserPostReactionRequest editUserPostReactionRequest = new EditUserPostReactionRequest();
                editUserPostReactionRequest.setPostReactionType(request.getPostReactionType());

                return edit(existingReaction.getId(), editUserPostReactionRequest);
            }
        }

        // if reaction does not exist on post, create it
        UserPostReaction postReaction = modelMapper.map(request, UserPostReaction.class);
        postReaction.setUserId(UserUtil.getCurrentUser().getId());

        return userPostReactionRepository.save(postReaction);
    }

    @Override
    public UserPostReaction edit(Long id, EditUserPostReactionRequest request) {
        UserPostReaction exitingPostReaction = reactionQueryService.getById(id);

        // if reaction of same type exists, throw error
        if (exitingPostReaction.getPostReactionType().equals(request.getPostReactionType())) {
            throw new DuplicateReactionException("The specified reaction has already been applied to the post");
        }

        // if reaction of different type exists, update the type to the specified type
        exitingPostReaction.setPostReactionType(request.getPostReactionType());

        return userPostReactionRepository.save(exitingPostReaction);
    }

    @Override
    public void delete(Long id) {
        userPostReactionRepository.delete(reactionQueryService.getById(id));
    }
}
