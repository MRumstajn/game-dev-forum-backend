package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchPostRequest;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {
    private final PostRepository postRepository;

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));
    }

    @Override
    public List<Post> search(SearchPostRequest request) {
        return postRepository.findByThreadId(request.getThreadId());
    }

    @Override
    public Long getTotalCount() {
        return postRepository.count();
    }
}
