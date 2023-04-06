package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;

    private final PostQueryService postQueryService;

    private final UserPostReactionCommandService userPostReactionCommandService;

    private final ForumUserQueryService forumUserQueryService;

    private final ModelMapper modelMapper;

    @Override
    public Post create(CreatePostRequest request) {
        Post newPost = modelMapper.map(request, Post.class);
        newPost.setCreationDateTime(LocalDateTime.now());
        newPost.setAuthor(forumUserQueryService.getById(request.getAuthorId()));

        return postRepository.save(newPost);
    }

    @Override
    public Post edit(Long id, EditPostRequest request) {
        Post existingPost = postQueryService.getById(id);

        if (UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN && !isCurrentUserPostOwner(existingPost)){
            throw new UnauthorizedActionException("User is not the creator of this post");
        }

        modelMapper.map(request, existingPost);

        return postRepository.save(existingPost);
    }

    @Override
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
