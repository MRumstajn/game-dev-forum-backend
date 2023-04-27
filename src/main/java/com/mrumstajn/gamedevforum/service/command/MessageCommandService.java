package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateMessageRequest;
import com.mrumstajn.gamedevforum.dto.request.EditMessageRequest;
import com.mrumstajn.gamedevforum.entity.Message;

public interface MessageCommandService {
    Message create(CreateMessageRequest request);

    Message edit(Long id, EditMessageRequest request);

    void delete(Long id);

    Message markAsRead(Long id);
}
