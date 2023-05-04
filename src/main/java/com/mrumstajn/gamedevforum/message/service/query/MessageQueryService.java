package com.mrumstajn.gamedevforum.message.service.query;

import com.mrumstajn.gamedevforum.message.dto.request.SearchMessagesRequestPageable;
import com.mrumstajn.gamedevforum.message.entity.Message;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageQueryService {
    List<Message> getAllById(List<Long> ids);

    List<Message> getAllByConversationId(Long id);

    Page<Message> searchPageable(SearchMessagesRequestPageable requestPageable);

    Long countUnreadByConversationId(Long conversationId);

    LocalDateTime getLatestDateTimeByConversationId(Long id);
}
