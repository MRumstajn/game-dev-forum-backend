package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchLatestPostRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchPostRequest;
import com.mrumstajn.gamedevforum.entity.Post;

import java.util.List;

public interface PostQueryService {

    Post getById(Long id);

    List<Post> search(SearchPostRequest request);

    Long getTotalCount();

    Long getTotalCountByThreadId(Long threadId);

    Post getLatest(SearchLatestPostRequest request);
}
