package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.dto.request.SubscribeToThreadRequest;
import com.mrumstajn.gamedevforum.entity.*;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.command.ThreadSubscriptionCommandService;
import com.mrumstajn.gamedevforum.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.service.query.ThreadSubscriptionQueryService;
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
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;

    private final PostQueryService postQueryService;

    private final UserPostReactionCommandService userPostReactionCommandService;

    private final UserFollowerQueryService userFollowerQueryService;

    private final NotificationCommandService notificationCommandService;

    private final ForumThreadQueryService forumThreadQueryService;

    private final ThreadSubscriptionQueryService threadSubscriptionQueryService;

    private final ThreadSubscriptionCommandService threadSubscriptionCommandService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Post create(CreatePostRequest request) {
        // only allow admins to make posts in news section threads
        ForumThread targetThread = forumThreadQueryService.getById(request.getThreadIdentifier());
        if (targetThread.getCategory().getTitle().equalsIgnoreCase("news") &&
                UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN){
            throw new UnauthorizedActionException("Only ADMIN users can create posts in this thread");
        }

        ForumUser currentUser = UserUtil.getCurrentUser();

        Post newPost = modelMapper.map(request, Post.class);
        newPost.setCreationDateTime(LocalDateTime.now());
        newPost.setAuthor(currentUser);
        newPost.setThread(targetThread);

        postRepository.save(newPost);

        // send notification to user followers about the new post
        List<Long> followerIds = userFollowerQueryService.getFollowersByFollowedUserId(currentUser.getId())
                .stream().map(ForumUser::getId).toList();

        sendNewPostNotificationTo(followerIds, targetThread.getTitle(), currentUser.getUsername());

        // if user is not subscribed to this thread, subscribe them
        List<Long> subscriberIds = threadSubscriptionQueryService.getAllByThreadId(request.getThreadIdentifier())
                .stream().map(threadSubscription -> threadSubscription.getUser().getId()).toList();

        if (!subscriberIds.contains(currentUser.getId())){
            threadSubscriptionCommandService.create(new SubscribeToThreadRequest(targetThread.getId()));
        }

        // send notification to thread subscribers (except the author) that are not followers of the post author
        // this follower check prevents notification from being sent twice.
        List<Long> filteredSubscriberIds = subscriberIds.stream()
                .filter(subscriberId -> !followerIds.contains(subscriberId) && !Objects.equals(subscriberId, currentUser.getId())).toList();

        sendNewPostNotificationTo(filteredSubscriberIds, targetThread.getTitle(), UserUtil.getCurrentUser().getUsername());

        return newPost;
    }

    @Override
    @Transactional
    public Post edit(Long id, EditPostRequest request) {
        Post existingPost = postQueryService.getById(id);

        if (!UserUtil.isUserAdmin() && !isCurrentUserPostOwner(existingPost)){
            throw new UnauthorizedActionException("User is not the creator of this post");
        }

        modelMapper.map(request, existingPost);

        return postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Post existingPost = postQueryService.getById(id);

        if (!UserUtil.isUserAdmin() && !isCurrentUserPostOwner(existingPost)){
            throw new UnauthorizedActionException("User is not the creator of this post");
        }

        userPostReactionCommandService.deleteAllByPostId(existingPost.getId());
        postRepository.delete(existingPost);
    }

    private boolean isCurrentUserPostOwner(Post post){
        return Objects.equals(UserUtil.getCurrentUser().getId(), post.getAuthor().getId());
    }

    private boolean isCurrentUserThreadOwner(ForumThread thread){
        return Objects.equals(UserUtil.getCurrentUser().getId(), thread.getAuthor().getId());
    }

    private void sendNewPostNotificationTo(
            List<Long> recipientIds, String threadTitle, String followedUserUsername){

        notificationCommandService.createAll(recipientIds.stream().map(id -> {
            CreateNotificationRequest notificationRequest = new CreateNotificationRequest();
            notificationRequest.setRecipientId(id);
            notificationRequest.setTitle("New post");
            notificationRequest.setContent(followedUserUsername + " has made a new post in " + threadTitle);

            return notificationRequest;
        }).toList());
    }
}
