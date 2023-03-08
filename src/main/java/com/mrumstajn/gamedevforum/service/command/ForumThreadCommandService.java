package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;

public interface ForumThreadCommandService {

    ForumThread create(CreateForumThreadRequest request);
}
