package com.mrumstajn.gamedevforum.auth.service.command;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;

public interface JWTCommandService {

    String generateTokenForUser(ForumUser user);
}
