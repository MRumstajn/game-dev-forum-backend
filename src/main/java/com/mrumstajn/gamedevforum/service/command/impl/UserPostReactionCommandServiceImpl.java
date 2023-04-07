package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.exception.DuplicateReactionException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.UserPostReactionRepository;

import com.mrumstajn.gamedevforum.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.service.query.UserPostReactionQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserPostReactionCommandServiceImpl implements UserPostReactionCommandService {
    private final UserPostReactionRepository userPostReactionRepository;

    private final UserPostReactionQueryService reactionQueryService;

    private final PostQueryService postQueryService;

    private final ModelMapper modelMapper;

    @Override
    public UserPostReaction create(CreateUserPostReactionRequest request) {
        postQueryService.getById(request.getPostIdentifier()); // check if post exists first

        // check if same reaction already exists for the post and the current user
        SearchUserPostReactionRequest userPostReactionRequest = new SearchUserPostReactionRequest();
        userPostReactionRequest.setUserId(UserUtil.getCurrentUser().getId());
        userPostReactionRequest.setPostIds(List.of(request.getPostIdentifier()));

        List<UserPostReaction> matches = reactionQueryService.search(userPostReactionRequest);
        matches.removeIf(element -> !Objects.equals(element.getPostId(), request.getPostIdentifier()));
        if (matches.size() > 0) {
            UserPostReaction existingReaction = matches.get(0);
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
        }

        // if reaction does not exist on post, create it
        UserPostReaction postReaction = modelMapper.map(request, UserPostReaction.class);
        postReaction.setUserId(UserUtil.getCurrentUser().getId());
        postReaction.setPostId(request.getPostIdentifier());

        return userPostReactionRepository.save(postReaction);
    }

    @Override
    public UserPostReaction edit(Long id, EditUserPostReactionRequest request) {
        UserPostReaction existingPostReaction = reactionQueryService.getById(id);

        if (UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN && !isCurrentUserReactionOwner(existingPostReaction)){
            throw new UnauthorizedActionException("User is not the creator of this reaction");
        }

        // if reaction of same type exists, throw error
        if (existingPostReaction.getPostReactionType().equals(request.getPostReactionType())) {
            throw new DuplicateReactionException("The specified reaction has already been applied to the post");
        }

        // if reaction of different type exists, update the type to the specified type
        existingPostReaction.setPostReactionType(request.getPostReactionType());

        return userPostReactionRepository.save(existingPostReaction);
    }

    @Override
    public void delete(Long id) {
        UserPostReaction existingPostReaction = reactionQueryService.getById(id);

        if (UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN && !isCurrentUserReactionOwner(existingPostReaction)){
            throw new UnauthorizedActionException("User is not the creator of this reaction");
        }

        userPostReactionRepository.delete(existingPostReaction);
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        userPostReactionRepository.deleteAllByPostId(postId);
    }

    private boolean isCurrentUserReactionOwner(UserPostReaction reaction){
        return Objects.equals(reaction.getUserId(), UserUtil.getCurrentUser().getId());
    }
}
