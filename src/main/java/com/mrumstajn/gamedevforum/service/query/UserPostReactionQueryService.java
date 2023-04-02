package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.entity.UserPostReaction;

import java.util.List;

public interface UserPostReactionQueryService {

    UserPostReaction search(SearchUserPostReactionRequest request);

   List<PostReactionTypeCountResponse> getReactionCountForAll(SearchUserPostReactionCountRequest request);

   UserPostReaction getById(Long id);
}
