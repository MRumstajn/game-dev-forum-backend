package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.service.query.AuthorizationQueryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationQueryServiceImpl implements AuthorizationQueryService {

    @Override
    public ForumUser getCurrentUser() {
        return ((ForumUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
