package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateConversationRequest;
import com.mrumstajn.gamedevforum.entity.Conversation;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.repository.ConversationRepository;
import com.mrumstajn.gamedevforum.service.command.ConversationCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationCommandServiceImpl implements ConversationCommandService {
    private final ConversationRepository conversationRepository;

    private final ForumUserQueryService forumUserQueryService;

    @Override
    public Conversation create(CreateConversationRequest request) {
        Conversation newConversation = new Conversation();

        List<ForumUser> participants = forumUserQueryService.getAllById(List.of(request.getParticipantAId(), request.getParticipantBId()));

        if (participants.size() == 0 || participants.get(0) == null){
            throw new EntityNotFoundException("ParticipantA (id " + request.getParticipantAId() + ") does not exist");
        }
        if (participants.size() < 2 || participants.get(1) == null){
            throw new EntityNotFoundException("ParticipantB (id " + request.getParticipantBId() + ") does not exist");
        }

        newConversation.setParticipantA(participants.get(0));
        newConversation.setParticipantB(participants.get(1));

        return conversationRepository.save(newConversation);
    }
}
