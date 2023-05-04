package com.mrumstajn.gamedevforum.conversation.service.command;

import com.mrumstajn.gamedevforum.conversation.dto.request.CreateConversationRequest;
import com.mrumstajn.gamedevforum.conversation.entity.Conversation;

public interface ConversationCommandService {

    Conversation create(CreateConversationRequest request);
}
