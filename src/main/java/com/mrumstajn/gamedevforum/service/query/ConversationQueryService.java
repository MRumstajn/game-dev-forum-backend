package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchConversationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Conversation;
import org.springframework.data.domain.Page;

public interface ConversationQueryService {

    Conversation getById(Long id);

    Page<Conversation> searchPageableByCurrentUser(SearchConversationRequestPageable requestPageable);

    Boolean isUserParticipantInConversation(Long userId, Long conversationId);
}
