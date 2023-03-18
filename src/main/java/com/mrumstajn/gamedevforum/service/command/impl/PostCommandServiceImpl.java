package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;

    private final PostQueryService postQueryService;

    private final ForumUserQueryService forumUserQueryService;

    private final ModelMapper modelMapper;

    @Override
    public Post create(CreatePostRequest request) {
        Post newPost = modelMapper.map(request, Post.class);
        newPost.setCreationDate(LocalDate.now());
        newPost.setAuthor(forumUserQueryService.getById(request.getAuthorId()));

        return postRepository.save(newPost);
    }

    @Override
    public Post edit(Long id, EditPostRequest request) {
        Post existingPost = postQueryService.getById(id);
        modelMapper.map(request, existingPost);

        return postRepository.save(existingPost);
    }

    @Override
    public void delete(Long id) {
        Post existingPost = postQueryService.getById(id);

        postRepository.delete(existingPost);
    }

    @Override
    public Post increaseLikes(Long id) {
        Post existingPost = postQueryService.getById(id);
        existingPost.setLikes(existingPost.getLikes() + 1);

        return postRepository.save(existingPost);
    }

    @Override
    public Post increaseDislikes(Long id) {
        Post existingPost = postQueryService.getById(id);
        existingPost.setDislikes(existingPost.getDislikes() + 1);

        return postRepository.save(existingPost);
    }
}
