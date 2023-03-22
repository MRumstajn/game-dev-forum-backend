package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchLatestPostRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchPostRequest;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.repository.PostRepository;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
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
        SearchConfiguration<Post, Post, SearchPostRequest> searchConfiguration = SearchConfiguration.<Post, Post, SearchPostRequest>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(Post.class)
                .build();

        return postRepository.findAll(request, searchConfiguration);
    }

    @Override
    public Long getTotalCount() {
        return postRepository.count();
    }

    @Override
    public Long getTotalCountByThreadId(Long threadId) {
        return postRepository.countAllByThreadId(threadId);
    }

    @Override
    public Post getLatest(SearchLatestPostRequest request) {
        long postCount = postRepository.countAllByThreadId(request.getThreadId());

        if (postCount == 0){
            return null;
        }
        if (postCount < 2){
            return postRepository.findAll().get(0);
        }

        return postRepository.findLatestByCreationDateAndThreadId(request.getThreadId());
    }
}
