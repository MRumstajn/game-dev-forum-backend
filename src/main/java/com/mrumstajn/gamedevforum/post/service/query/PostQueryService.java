package com.mrumstajn.gamedevforum.post.service.query;

import com.mrumstajn.gamedevforum.post.dto.request.SearchThreadForPostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchPostRequestPageable;
import com.mrumstajn.gamedevforum.post.dto.response.TotalPostsResponse;
import com.mrumstajn.gamedevforum.post.entity.Post;
import org.springframework.data.domain.Page;

public interface PostQueryService {

    Post getById(Long id);

    Page<Post> search(SearchPostRequestPageable request);

    Long getTotalCount();

    Long getTotalCountByThreadId(Long threadId);

    Post getLatest(SearchThreadForPostRequest request);

    Post getTopByLikesInThread(Long threadId);

    Long getTotalPageCountForThread(Long threadId);

    TotalPostsResponse getTotalPostsInfoForThread(Long threadId);
}
