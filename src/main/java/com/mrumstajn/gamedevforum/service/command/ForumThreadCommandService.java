package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.EditForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;

public interface ForumThreadCommandService {

    ForumThread create(CreateForumThreadRequest request);

    ForumThread edit(Long id, EditForumThreadRequest request);

    void delete(Long id);
}
