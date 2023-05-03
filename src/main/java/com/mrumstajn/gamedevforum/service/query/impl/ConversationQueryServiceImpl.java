package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchConversationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Conversation;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.ConversationRepository;
import com.mrumstajn.gamedevforum.service.query.ConversationQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConversationQueryServiceImpl implements ConversationQueryService {
    private final ConversationRepository conversationRepository;

    @Override
    public Conversation getById(Long id) {
        Conversation conversation = conversationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Conversation with id " + id + " does not exist"));

        if (!isUserParticipantInFetchedConversation(conversation, UserUtil.getCurrentUser())){
            throw new UnauthorizedActionException("User is not participating in this conversation");
        }

        return conversation;
    }

    @Override
    public Page<Conversation> searchPageableByCurrentUser(SearchConversationRequestPageable requestPageable) {
        return conversationRepository.findAllByCurrentUser(UserUtil.getCurrentUser().getId(), PageableUtil.convertToPageable(requestPageable));
    }

    @Override
    public Boolean isUserParticipantInConversation(Long userId, Long conversationId) {
        return conversationRepository.existsByAnyParticipant(conversationId, userId);
    }

    @Override
    public Conversation getByParticipantIds(Long participantAId, Long participantBId) {
        return conversationRepository.findFirstByParticipantAIdAndParticipantBId(participantAId, participantBId);
    }

    private Boolean isUserParticipantInFetchedConversation(Conversation conversation, ForumUser user) {
        return Objects.equals(conversation.getParticipantA().getId(), user.getId()) ||
                Objects.equals(conversation.getParticipantB().getId(), user.getId());
    }
}
