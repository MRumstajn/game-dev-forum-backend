package com.mrumstajn.gamedevforum.conversation.service.query;

import com.mrumstajn.gamedevforum.conversation.dto.request.SearchConversationRequestPageable;
import com.mrumstajn.gamedevforum.conversation.entity.Conversation;
import org.springframework.data.domain.Page;

public interface ConversationQueryService {

    Conversation getById(Long id);

    Page<Conversation> searchPageableByCurrentUser(SearchConversationRequestPageable requestPageable);

    Boolean isUserParticipantInConversation(Long userId, Long conversationId);

    Conversation getByParticipantIds(Long participantAId, Long participantBId);
}
