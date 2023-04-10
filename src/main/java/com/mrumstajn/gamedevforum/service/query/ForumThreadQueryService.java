package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchForumThreadRequestPageable;
import com.mrumstajn.gamedevforum.dto.request.SearchLatestForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import org.springframework.data.domain.Page;

public interface ForumThreadQueryService {

    ForumThread getById(Long id);

    Page<ForumThread> search(SearchForumThreadRequestPageable request);

    Long getTotalCount();

    Long getTotalCountByCategoryId(Long categoryId);

    ForumThread getLatest(SearchLatestForumThreadRequest request);
}
