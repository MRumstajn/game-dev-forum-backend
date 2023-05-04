package com.mrumstajn.gamedevforum.auth.service.query;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;

public interface AuthorizationQueryService {

    ForumUser getCurrentUser();
}
