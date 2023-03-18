package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;

import java.util.List;

public interface ForumThreadQueryService {

    ForumThread getById(Long id);

    List<ForumThread> search(SearchForumThreadRequest request);

    Long getTotalCount();

    Long getTotalCountByCategoryId(Long categoryId);
}
