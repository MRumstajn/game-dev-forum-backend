package com.mrumstajn.gamedevforum.thread.service.command;

import com.mrumstajn.gamedevforum.thread.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.thread.dto.request.EditForumThreadRequest;
import com.mrumstajn.gamedevforum.thread.entity.ForumThread;

public interface ForumThreadCommandService {

    ForumThread create(CreateForumThreadRequest request);

    ForumThread edit(Long id, EditForumThreadRequest request);

    void delete(Long id);
}
