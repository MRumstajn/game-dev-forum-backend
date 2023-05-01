package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchMessagesRequestPageable;
import com.mrumstajn.gamedevforum.entity.Message;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.MessageRepository;
import com.mrumstajn.gamedevforum.service.query.ConversationQueryService;
import com.mrumstajn.gamedevforum.service.query.MessageQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageQueryServiceImpl implements MessageQueryService {
    private final MessageRepository messageRepository;

    private final ConversationQueryService conversationQueryService;

    @Override
    public List<Message> getAllById(List<Long> ids) {
        return messageRepository.findAllById(ids);
    }

    @Override
    public List<Message> getAllByConversationId(Long id) {
        return messageRepository.getAllByConversationId(id);
    }


    @Override
    public Page<Message> searchPageable(SearchMessagesRequestPageable requestPageable) {
        if (!conversationQueryService.isUserParticipantInConversation(UserUtil.getCurrentUser().getId(), requestPageable.getConversationId())){
            throw new UnauthorizedActionException("User is not a participant of the specified conversation");
        }

        SearchConfiguration<Message, Message, SearchMessagesRequestPageable> searchConfiguration =
                SearchConfiguration.<Message, Message, SearchMessagesRequestPageable>builder()
                        .resolvePropertyMappingUsingPrefix(true)
                        .resultClass(Message.class)
                        .build();

        return messageRepository.findAll(requestPageable, searchConfiguration, PageableUtil.convertToPageable(requestPageable));
    }

    @Override
    public Long countUnreadByConversationId(Long conversationId) {
        return messageRepository.countAllByConversationIdAndReadersNotContaining(conversationId, UserUtil.getCurrentUser());
    }

    @Override
    public LocalDateTime getLatestDateTimeByConversationId(Long conversationId) {

        Message latestMessage = messageRepository.getTopByConversationId(conversationId);
        if (latestMessage != null){
            return latestMessage.getCreationDateTime();
        }

        return null;
    }
}
