package com.mrumstajn.gamedevforum.post.service.query.impl;

import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import com.mrumstajn.gamedevforum.post.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.post.repository.UserPostReactionRepository;
import com.mrumstajn.gamedevforum.post.service.query.UserPostReactionQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserPostReactionQueryServiceImpl implements UserPostReactionQueryService {
    private final UserPostReactionRepository userPostReactionRepository;

    @Override
    public List<UserPostReaction> search(SearchUserPostReactionRequest request) {
        SearchConfiguration<UserPostReaction, UserPostReaction, SearchUserPostReactionRequest> searchConfiguration =
                SearchConfiguration.<UserPostReaction, UserPostReaction, SearchUserPostReactionRequest>builder()
                        .resolvePropertyMappingUsingPrefix(true)
                        .resultClass(UserPostReaction.class)
                        .build();

        return userPostReactionRepository.findAll(request, searchConfiguration);
    }

    @Override
    public List<PostReactionTypeCountResponse> getReactionCountForAll(SearchUserPostReactionCountRequest request) {
        List<PostReactionTypeCountResponse> countResponses = new ArrayList<>();

        Arrays.stream(PostReactionType.values()).forEach(type -> request.getPostIds().forEach(postId -> {
            PostReactionTypeCountResponse response = new PostReactionTypeCountResponse();
            response.setPostId(postId);
            response.setPostReactionType(type);
            response.setCount(userPostReactionRepository.countAllByPostReactionTypeAndPostId(type, postId));

            countResponses.add(response);
        }));

        return countResponses;
    }

    @Override
    public Long countByPostIdAndReactionType(Long postId, PostReactionType reactionType) {
        return userPostReactionRepository.countAllByPostReactionTypeAndPostId(reactionType, postId);
    }

    @Override
    public UserPostReaction getById(Long id) {
        return userPostReactionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Post reaction with id " + id + " doe not exist"));
    }
}
