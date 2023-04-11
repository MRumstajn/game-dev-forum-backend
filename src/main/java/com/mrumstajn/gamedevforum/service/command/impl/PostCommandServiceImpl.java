package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.service.query.UserFollowerQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;

    private final PostQueryService postQueryService;

    private final UserPostReactionCommandService userPostReactionCommandService;

    private final UserFollowerQueryService userFollowerQueryService;

    private final NotificationCommandService notificationCommandService;

    private final ForumThreadQueryService forumThreadQueryService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Post create(CreatePostRequest request) {
        ForumUser currentUser = UserUtil.getCurrentUser();

        Post newPost = modelMapper.map(request, Post.class);
        newPost.setCreationDateTime(LocalDateTime.now());
        newPost.setAuthor(currentUser);
        newPost.setThreadId(request.getThreadIdentifier());

        postRepository.save(newPost);

        // send notification to user followers about the new post
        List<ForumUser> followers = userFollowerQueryService.getFollowersByFollowedUserId(UserUtil.getCurrentUser().getId());
        notificationCommandService.createAll(followers.stream().map(follower -> {
            ForumThread targetThread = forumThreadQueryService.getById(newPost.getThreadId());

            CreateNotificationRequest notificationRequest = new CreateNotificationRequest();
            notificationRequest.setRecipientId(follower.getId());
            notificationRequest.setTitle("New post");
            notificationRequest.setContent(currentUser.getUsername() + " has made a new post in " + targetThread.getTitle());

            return notificationRequest;
        }).toList());

        return newPost;
    }

    @Override
    @Transactional
    public Post edit(Long id, EditPostRequest request) {
        Post existingPost = postQueryService.getById(id);

        if (UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN && !isCurrentUserPostOwner(existingPost)){
            throw new UnauthorizedActionException("User is not the creator of this post");
        }

        modelMapper.map(request, existingPost);

        return postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Post existingPost = postQueryService.getById(id);

        if (UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN && !isCurrentUserPostOwner(existingPost)){
            throw new UnauthorizedActionException("User is not the creator of this post");
        }

        userPostReactionCommandService.deleteAllByPostId(existingPost.getId());
        postRepository.delete(existingPost);
    }

    private boolean isCurrentUserPostOwner(Post post){
        return Objects.equals(UserUtil.getCurrentUser().getId(), post.getAuthor().getId());
    }
}
