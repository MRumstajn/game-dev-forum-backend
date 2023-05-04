package com.mrumstajn.gamedevforum.thread.service.query;

import com.mrumstajn.gamedevforum.thread.dto.request.SearchForumThreadRequestPageable;
import com.mrumstajn.gamedevforum.thread.dto.request.SearchLatestForumThreadRequest;
import com.mrumstajn.gamedevforum.thread.entity.ForumThread;
import org.springframework.data.domain.Page;

public interface ForumThreadQueryService {

    ForumThread getById(Long id);

    Page<ForumThread> searchPageable(SearchForumThreadRequestPageable request);

    Long getTotalCount();

    Long getTotalCountByCategoryId(Long categoryId);

    ForumThread getLatest(SearchLatestForumThreadRequest request);
}
