package com.mrumstajn.gamedevforum.conversation.controller;

import com.mrumstajn.gamedevforum.conversation.dto.request.SearchConversationRequestPageable;
import com.mrumstajn.gamedevforum.conversation.dto.response.ConversationResponse;
import com.mrumstajn.gamedevforum.conversation.service.query.ConversationQueryService;
import com.mrumstajn.gamedevforum.message.service.query.MessageQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversations")
public class ConversationController {
    private final ConversationQueryService conversationQueryService;

    private final MessageQueryService messageQueryService;

    private final ModelMapper modelMapper;

    @PostMapping("/search")
    public ResponseEntity<Page<ConversationResponse>> searchPageable(@RequestBody @Valid SearchConversationRequestPageable requestPageable){
        return ResponseEntity.ok(conversationQueryService.searchPageableByCurrentUser(requestPageable)
                .map(conversation -> {
                    ConversationResponse conversationResponse = modelMapper.map(conversation, ConversationResponse.class);
                    conversationResponse.setUnreadMessages(messageQueryService.countUnreadByConversationId(conversation.getId()));
                    conversationResponse.setLatestMessageDateTime(messageQueryService.getLatestDateTimeByConversationId(conversation.getId()));
                    return conversationResponse;
                }));
    }
}
