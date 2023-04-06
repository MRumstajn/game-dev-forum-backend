package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.entity.ForumUser;

public interface JWTCommandService {

    String generateTokenForUser(ForumUser user);
}
