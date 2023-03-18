package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.entity.ForumUser;

public interface ForumUserQueryService {

    ForumUser getById(Long id);

    Long getTotalCount();
}
