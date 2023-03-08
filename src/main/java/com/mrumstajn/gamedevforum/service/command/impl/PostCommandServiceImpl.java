package com.mrumstajn.gamedevforum.service.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostCommandServiceImpl implements PostCommandService {
    private final PostRepository postRepository;

    private final ForumUserQueryService forumUserQueryService;

    private final ObjectMapper mapper;

    @Override
    public Post create(CreatePostRequest request) {
        Post newPost = mapper.convertValue(request, Post.class);
        newPost.setCreationDate(LocalDate.now());
        newPost.setAuthor(forumUserQueryService.getById(request.getAuthorId()));

        return postRepository.save(newPost);
    }
}
