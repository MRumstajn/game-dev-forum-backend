package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.*;
import com.mrumstajn.gamedevforum.entity.Conversation;
import com.mrumstajn.gamedevforum.entity.Message;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.MessageRepository;
import com.mrumstajn.gamedevforum.service.command.ConversationCommandService;
import com.mrumstajn.gamedevforum.service.command.MessageCommandService;
import com.mrumstajn.gamedevforum.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.service.query.ConversationQueryService;
import com.mrumstajn.gamedevforum.service.query.MessageQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageCommandServiceImpl implements MessageCommandService {
    private final MessageRepository messageRepository;

    private final MessageQueryService messageQueryService;

    private final ConversationQueryService conversationQueryService;

    private final ConversationCommandService conversationCommandService;

    private final NotificationCommandService notificationCommandService;

    @Override
    public Message create(CreateMessageRequest request) {
        // get or create conversation
        Conversation conversation;

        conversation = conversationQueryService.getByParticipantIds(UserUtil.getCurrentUser().getId(), request.getRecipientId());

        if (conversation == null) {
            CreateConversationRequest createConversationRequest = new CreateConversationRequest();
            createConversationRequest.setParticipantAId(UserUtil.getCurrentUser().getId());
            createConversationRequest.setParticipantBId(request.getRecipientId());

            conversation = conversationCommandService.create(createConversationRequest);
        }

        // create message
        Message newMessage = new Message();
        newMessage.setContent(request.getContent());
        newMessage.setAuthor(UserUtil.getCurrentUser());
        newMessage.setCreationDateTime(LocalDateTime.now());
        newMessage.setConversation(conversationQueryService.getById(conversation.getId()));
        newMessage.setReaders(List.of(UserUtil.getCurrentUser()));

        // create notification for recipient
        CreateNotificationRequest createNotificationRequest;
        if (!Objects.equals(conversation.getParticipantA().getId(), UserUtil.getCurrentUser().getId())) {
            createNotificationRequest = generateMessageNotificationRequest(conversation.getParticipantA().getId());
        } else {
            createNotificationRequest = generateMessageNotificationRequest(conversation.getParticipantB().getId());
        }

        notificationCommandService.createAll(List.of(createNotificationRequest));

        return messageRepository.save(newMessage);
    }

    @Override
    public Message edit(Long id, EditMessageRequest request) {
        Message existingMessage = messageQueryService.getAllById(List.of(id)).get(0);

        if (!isUserOwnerOfMessage(existingMessage) && !UserUtil.isUserAdmin()) {
            throw new UnauthorizedActionException("User is not the owner of the specified message");
        }

        existingMessage.setContent(request.getContent());

        return messageRepository.save(existingMessage);
    }

    @Override
    public void delete(Long id) {
        Message existingMessage = messageQueryService.getAllById(List.of(id)).get(0);

        if (!isUserOwnerOfMessage(existingMessage) && !UserUtil.isUserAdmin()) {
            throw new UnauthorizedActionException("User is not the owner of the specified message");
        }

        messageRepository.delete(existingMessage);
    }

    @Override
    public List<Message> markAllAsRead(MarkMessagesAsReadRequest request) {
        List<Message> existingMessages = new ArrayList<>();
        if (request.getConversationId() != null) {
            existingMessages = messageQueryService.getAllByConversationId(request.getConversationId());
        } else {
            messageQueryService.getAllById(request.getMessageIds());
        }


        existingMessages.forEach(existingMessage -> {
            if (!conversationQueryService.isUserParticipantInConversation(UserUtil.getCurrentUser().getId(), existingMessage.getConversation().getId())) {
                throw new UnauthorizedActionException("User is not a part of the conversation this message is in");
            }

            if (existingMessage.getReaders().contains(UserUtil.getCurrentUser())) {
                throw new DuplicateResourceException("Message is already marked as read");
            }

            if (!existingMessage.getReaders().contains(UserUtil.getCurrentUser())) {
                existingMessage.getReaders().add(UserUtil.getCurrentUser());
            }
        });

        return messageRepository.saveAll(existingMessages);
    }

    private boolean isUserOwnerOfMessage(Message message) {
        return Objects.equals(message.getAuthor().getId(), UserUtil.getCurrentUser().getId());
    }

    private CreateNotificationRequest generateMessageNotificationRequest(Long id) {
        CreateNotificationRequest notificationRequest = new CreateNotificationRequest();
        notificationRequest.setRecipientId(id);
        notificationRequest.setTitle("New message");
        notificationRequest.setContent("You received a new message from " + UserUtil.getCurrentUser().getUsername());

        return notificationRequest;
    }
}
