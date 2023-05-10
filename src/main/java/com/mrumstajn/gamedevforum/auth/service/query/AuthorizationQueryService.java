package com.mrumstajn.gamedevforum.auth.service.query;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthorizationQueryService {

    ForumUser getCurrentUser();

    UsernamePasswordAuthenticationToken getAuthenticationFromJWTToken(String token);
}
