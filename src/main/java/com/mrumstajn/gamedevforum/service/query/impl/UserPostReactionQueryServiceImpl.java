package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.entity.PostReactionType;
import com.mrumstajn.gamedevforum.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.repository.UserPostReactionRepository;
import com.mrumstajn.gamedevforum.service.query.UserPostReactionQueryService;
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
    public UserPostReaction search(SearchUserPostReactionRequest request) {
        SearchConfiguration<UserPostReaction, UserPostReaction, SearchUserPostReactionRequest> searchConfiguration =
                SearchConfiguration.<UserPostReaction, UserPostReaction, SearchUserPostReactionRequest>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .build();

        List<UserPostReaction> reactions = userPostReactionRepository.findAll(request, searchConfiguration);
        if (reactions.size() == 0){
            return null;
        }

        return reactions.get(0);
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
    public UserPostReaction getById(Long id) {
        return userPostReactionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Post reaction with id " + id + " doe not exist"));
    }
}
