package com.mrumstajn.gamedevforum.message.controller;

import com.mrumstajn.gamedevforum.message.dto.request.CreateMessageRequest;
import com.mrumstajn.gamedevforum.message.dto.request.EditMessageRequest;
import com.mrumstajn.gamedevforum.message.dto.request.MarkMessagesAsReadRequest;
import com.mrumstajn.gamedevforum.message.dto.request.SearchMessagesRequestPageable;
import com.mrumstajn.gamedevforum.message.dto.response.MessageResponse;
import com.mrumstajn.gamedevforum.message.entity.Message;
import com.mrumstajn.gamedevforum.message.service.command.MessageCommandService;
import com.mrumstajn.gamedevforum.message.service.query.MessageQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageQueryService messageQueryService;

    private final MessageCommandService messageCommandService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid CreateMessageRequest request) {
        Message newMessage = messageCommandService.create(request);

        return ResponseEntity.created(URI.create("/messages/" + newMessage.getId()))
                .body(convertMessageToMessageResponse(newMessage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> edit(@PathVariable Long id, @RequestBody @Valid EditMessageRequest request) {
        return ResponseEntity.ok(convertMessageToMessageResponse(messageCommandService.edit(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        messageCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<List<MessageResponse>> markAsRead(@RequestBody MarkMessagesAsReadRequest request) {
        return ResponseEntity.ok(messageCommandService.markAllAsRead(request).stream().map(this::convertMessageToMessageResponse).toList());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<MessageResponse>> searchPageable(@RequestBody SearchMessagesRequestPageable requestPageable) {
        return ResponseEntity.ok(messageQueryService.searchPageable(requestPageable).map(this::convertMessageToMessageResponse));
    }

    private MessageResponse convertMessageToMessageResponse(Message message) {
        MessageResponse response = modelMapper.map(message, MessageResponse.class);
        response.setIsRead(hasUserReadMessage(message));

        return response;
    }

    private boolean hasUserReadMessage(Message message) {
        return message.getReaders().stream().filter(reader -> Objects.equals(reader.getId(),
                UserUtil.getCurrentUser().getId())).toList().size() > 0;
    }
}
