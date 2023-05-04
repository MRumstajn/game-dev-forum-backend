package com.mrumstajn.gamedevforum.message.service.command;

import com.mrumstajn.gamedevforum.message.dto.request.CreateMessageRequest;
import com.mrumstajn.gamedevforum.message.dto.request.EditMessageRequest;
import com.mrumstajn.gamedevforum.message.dto.request.MarkMessagesAsReadRequest;
import com.mrumstajn.gamedevforum.message.entity.Message;

import java.util.List;

public interface MessageCommandService {
    Message create(CreateMessageRequest request);

    Message edit(Long id, EditMessageRequest request);

    void delete(Long id);

    List<Message> markAllAsRead(MarkMessagesAsReadRequest request);
}
