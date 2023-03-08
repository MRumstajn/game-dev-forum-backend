package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.entity.ForumUser;

public interface ForumUserCommandService {

    ForumUser create(CreateForumUserRequest request);
}
