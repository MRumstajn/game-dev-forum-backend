package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateConversationRequest;
import com.mrumstajn.gamedevforum.entity.Conversation;
import com.mrumstajn.gamedevforum.repository.ConversationRepository;
import com.mrumstajn.gamedevforum.service.command.ConversationCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationCommandServiceImpl implements ConversationCommandService {
    private final ConversationRepository conversationRepository;

    private final ForumUserQueryService forumUserQueryService;

    @Override
    public Conversation create(CreateConversationRequest request) {
        Conversation newConversation = new Conversation();
        newConversation.setParticipants(forumUserQueryService.getAllById(request.getParticipants()));

        return conversationRepository.save(newConversation);
    }
}
