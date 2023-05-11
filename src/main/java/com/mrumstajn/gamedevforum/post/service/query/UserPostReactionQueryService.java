package com.mrumstajn.gamedevforum.post.service.query;

import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.post.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import com.mrumstajn.gamedevforum.post.entity.UserPostReaction;

import java.util.List;

public interface UserPostReactionQueryService {

    List<UserPostReaction> search(SearchUserPostReactionRequest request);

   List<PostReactionTypeCountResponse> getReactionCountForAll(SearchUserPostReactionCountRequest request);

   Long countByPostIdAndReactionType(Long postId, PostReactionType reactionType);

   UserPostReaction getById(Long id);
}
