package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateConversationRequest;
import com.mrumstajn.gamedevforum.entity.Conversation;

public interface ConversationCommandService {

    Conversation create(CreateConversationRequest request);
}
