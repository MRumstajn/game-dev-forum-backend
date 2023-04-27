package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchConversationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Conversation;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.ConversationRepository;
import com.mrumstajn.gamedevforum.service.query.ConversationQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
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

        if (conversation.getParticipants().stream().filter(participant -> Objects.equals(participant.getId(),
                UserUtil.getCurrentUser().getId())).toList().size() == 0){
            throw new UnauthorizedActionException("User is not a part of the specified conversation");
        }

        return conversation;
    }

    @Override
    public Page<Conversation> searchPageableByCurrentUser(SearchConversationRequestPageable requestPageable) {
        SearchConfiguration<Conversation, Conversation, SearchConversationRequestPageable> searchConfiguration =
                SearchConfiguration.<Conversation, Conversation, SearchConversationRequestPageable>builder()
                        .resolvePropertyMappingUsingPrefix(true)
                        .resultClass(Conversation.class)
                        .build();

        return conversationRepository.findAll(requestPageable, searchConfiguration, PageableUtil.convertToPageable(requestPageable));
    }

    @Override
    public Boolean isUserParticipantInConversation(Long userId, Long conversationId) {
        return conversationRepository.existsByParticipantsIdAndId(userId, conversationId);
    }
}
