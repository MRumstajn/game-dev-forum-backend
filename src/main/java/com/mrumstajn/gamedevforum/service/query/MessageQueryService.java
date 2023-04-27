package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchMessagesRequestPageable;
import com.mrumstajn.gamedevforum.entity.Message;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface MessageQueryService {
    Message getById(Long id);

    Page<Message> searchPageable(SearchMessagesRequestPageable requestPageable);

    Long countUnreadByConversationId(Long conversationId);

    LocalDateTime getLatestDateTimeByConversationId(Long id);
}
