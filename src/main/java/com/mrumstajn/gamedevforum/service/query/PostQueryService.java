package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchLatestPostRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchPostRequestPageable;
import com.mrumstajn.gamedevforum.entity.Post;
import org.springframework.data.domain.Page;

public interface PostQueryService {

    Post getById(Long id);

    Page<Post> search(SearchPostRequestPageable request);

    Long getTotalCount();

    Long getTotalCountByThreadId(Long threadId);

    Post getLatest(SearchLatestPostRequest request);
}
