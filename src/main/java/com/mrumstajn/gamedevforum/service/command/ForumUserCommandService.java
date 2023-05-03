package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.ChangeForumUserPasswordRequest;
import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.dto.request.EditForumUserRequest;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;

public interface ForumUserCommandService {

    ForumUser create(CreateForumUserRequest request);

    ForumUser edit(Long id, EditForumUserRequest request);

    ForumUser changePassword(Long id, ChangeForumUserPasswordRequest request);

    ForumUser grantRole(Long id, ForumUserRole role);
}
