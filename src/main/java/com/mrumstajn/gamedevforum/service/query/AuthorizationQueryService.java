package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.entity.ForumUser;

public interface AuthorizationQueryService {

    ForumUser getCurrentUser();
}
