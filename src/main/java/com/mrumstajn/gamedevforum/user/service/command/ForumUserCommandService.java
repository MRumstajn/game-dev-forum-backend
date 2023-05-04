package com.mrumstajn.gamedevforum.user.service.command;

import com.mrumstajn.gamedevforum.auth.service.dto.request.ChangeForumUserPasswordRequest;
import com.mrumstajn.gamedevforum.user.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.user.dto.request.EditForumUserRequest;
import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.entity.ForumUserRole;

public interface ForumUserCommandService {

    ForumUser create(CreateForumUserRequest request);

    ForumUser edit(Long id, EditForumUserRequest request);

    ForumUser changePassword(Long id, ChangeForumUserPasswordRequest request);

    ForumUser grantRole(Long id, ForumUserRole role);
}
