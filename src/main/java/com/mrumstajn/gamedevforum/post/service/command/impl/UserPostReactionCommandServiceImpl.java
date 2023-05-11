package com.mrumstajn.gamedevforum.post.service.command.impl;

import com.mrumstajn.gamedevforum.common.constants.UserConstants;
import com.mrumstajn.gamedevforum.post.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.entity.Post;
import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import com.mrumstajn.gamedevforum.post.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.post.repository.UserPostReactionRepository;

import com.mrumstajn.gamedevforum.post.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.post.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.post.service.query.UserPostReactionQueryService;
import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.service.command.ForumUserCommandService;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserPostReactionCommandServiceImpl implements UserPostReactionCommandService {
    private final UserPostReactionRepository userPostReactionRepository;

    private final UserPostReactionQueryService userPostReactionQueryService;

    private final UserPostReactionQueryService reactionQueryService;

    private final PostQueryService postQueryService;

    private final ForumUserQueryService forumUserQueryService;

    private final ForumUserCommandService forumUserCommandService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserPostReaction create(CreateUserPostReactionRequest request) {
        Post post = postQueryService.getById(request.getPostIdentifier()); // check if post exists first

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
                    throw new DuplicateResourceException("The specified reaction has already been applied to the post");
                } else {
                    // if reaction of different type exists, update the type to the specified type
                    EditUserPostReactionRequest editUserPostReactionRequest = new EditUserPostReactionRequest();
                    editUserPostReactionRequest.setPostReactionType(request.getPostReactionType());

                    changeAuthorsReputation(post.getAuthor().getId(), editUserPostReactionRequest.getPostReactionType() == PostReactionType.LIKE);

                    return edit(existingReaction.getId(), editUserPostReactionRequest);
                }
            }
        }

        // if reaction does not exist on post, create it
        UserPostReaction postReaction = modelMapper.map(request, UserPostReaction.class);
        postReaction.setUserId(UserUtil.getCurrentUser().getId());
        postReaction.setPostId(request.getPostIdentifier());

        changeAuthorsReputation(post.getAuthor().getId(), postReaction.getPostReactionType() == PostReactionType.LIKE);

    return userPostReactionRepository.save(postReaction);
    }

    @Override
    @Transactional
    public UserPostReaction edit(Long id, EditUserPostReactionRequest request) {
        UserPostReaction existingPostReaction = reactionQueryService.getById(id);
        Post post = postQueryService.getById(existingPostReaction.getPostId());

        if (!UserUtil.isUserAdmin() && !isCurrentUserReactionOwner(existingPostReaction)){
            throw new UnauthorizedActionException("User is not the creator of this reaction");
        }

        // if reaction of same type exists, throw error
        if (existingPostReaction.getPostReactionType().equals(request.getPostReactionType())) {
            throw new DuplicateResourceException("The specified reaction has already been applied to the post");
        }

        // if reaction of different type exists, update the type to the specified type
        existingPostReaction.setPostReactionType(request.getPostReactionType());

        changeAuthorsReputation(post.getAuthor().getId(), existingPostReaction.getPostReactionType() == PostReactionType.LIKE);

        return userPostReactionRepository.save(existingPostReaction);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        UserPostReaction existingPostReaction = reactionQueryService.getById(id);
        Post post = postQueryService.getById(existingPostReaction.getPostId());

        if (!UserUtil.isUserAdmin() && !isCurrentUserReactionOwner(existingPostReaction)){
            throw new UnauthorizedActionException("User is not the creator of this reaction");
        }

        changeAuthorsReputation(post.getAuthor().getId(), existingPostReaction.getPostReactionType() != PostReactionType.LIKE);

        userPostReactionRepository.delete(existingPostReaction);
    }

    @Override
    @Transactional
    public void deleteAllByPostId(Long postId) {
        Post post = postQueryService.getById(postId);

        Long likesRemoved = userPostReactionQueryService.countByPostIdAndReactionType(postId, PostReactionType.LIKE);
        Long dislikesRemoved = userPostReactionQueryService.countByPostIdAndReactionType(postId, PostReactionType.DISLIKE);
        Long newReputation = UserUtil.getCurrentUser().getReputation();

        if (dislikesRemoved != null) {
            newReputation += dislikesRemoved;
        }
        if (likesRemoved != null) {
            newReputation -= likesRemoved;
        }

        changeAuthorsReputation(post.getAuthor().getId(), newReputation);

        userPostReactionRepository.deleteAllByPostId(postId);
    }

    private boolean isCurrentUserReactionOwner(UserPostReaction reaction){
        return Objects.equals(reaction.getUserId(), UserUtil.getCurrentUser().getId());
    }

    private void changeAuthorsReputation(Long userId, boolean add){
        ForumUser author = forumUserQueryService.getById(userId);

        if (add && author.getReputation() < UserConstants.MAX_USER_REPUTATION){
            forumUserCommandService.editReputation(author.getId(), author.getReputation() + 1L);
        } else if (!add){
            forumUserCommandService.editReputation(author.getId(), author.getReputation() - 1L);
        }
    }

    private void changeAuthorsReputation(Long userId, Long reputation){
        ForumUser author = forumUserQueryService.getById(userId);
        forumUserCommandService.editReputation(author.getId(), reputation);
    }
}
