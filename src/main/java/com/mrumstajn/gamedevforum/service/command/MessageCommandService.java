package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateMessageRequest;
import com.mrumstajn.gamedevforum.dto.request.EditMessageRequest;
import com.mrumstajn.gamedevforum.dto.request.MarkMessagesAsReadRequest;
import com.mrumstajn.gamedevforum.entity.Message;

import java.util.List;

public interface MessageCommandService {
    Message create(CreateMessageRequest request);

    Message edit(Long id, EditMessageRequest request);

    void delete(Long id);

    List<Message> markAllAsRead(MarkMessagesAsReadRequest request);
}
