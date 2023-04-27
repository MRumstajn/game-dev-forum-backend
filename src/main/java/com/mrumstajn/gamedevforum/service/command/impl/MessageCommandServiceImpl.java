package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateMessageRequest;
import com.mrumstajn.gamedevforum.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.dto.request.EditMessageRequest;
import com.mrumstajn.gamedevforum.entity.Message;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.MessageRepository;
import com.mrumstajn.gamedevforum.service.command.MessageCommandService;
import com.mrumstajn.gamedevforum.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.service.query.ConversationQueryService;
import com.mrumstajn.gamedevforum.service.query.MessageQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final NotificationCommandService notificationCommandService;

    private final ModelMapper modelMapper;

    @Override
    public Message create(CreateMessageRequest request) {
        Message newMessage = modelMapper.map(request, Message.class);
        newMessage.setAuthor(UserUtil.getCurrentUser());
        newMessage.setCreationDateTime(LocalDateTime.now());
        newMessage.setConversation(conversationQueryService.getById(request.getConversationId()));
        newMessage.setReaders(List.of(UserUtil.getCurrentUser()));

        List<CreateNotificationRequest> notificationRequests = new ArrayList<>();
        conversationQueryService.getById(request.getConversationId()).getParticipants().forEach(participant -> {
            if (!Objects.equals(participant.getId(), UserUtil.getCurrentUser().getId())) {
                CreateNotificationRequest notificationRequest = new CreateNotificationRequest();
                notificationRequest.setRecipientId(participant.getId());
                notificationRequest.setTitle("New message");
                notificationRequest.setContent("You received a new message from " + UserUtil.getCurrentUser().getUsername());
                notificationRequests.add(notificationRequest);
            }
        });

        notificationCommandService.createAll(notificationRequests);

        return messageRepository.save(newMessage);
    }

    @Override
    public Message edit(Long id, EditMessageRequest request) {
        Message existingMessage = messageQueryService.getById(id);

        if (!isUserOwnerOfMessage(existingMessage) && !UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("User is not the owner of the specified message");
        }

        existingMessage.setContent(request.getContent());

        return messageRepository.save(existingMessage);
    }

    @Override
    public void delete(Long id) {
        Message existingMessage = messageQueryService.getById(id);

        if (!isUserOwnerOfMessage(existingMessage) && !UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("User is not the owner of the specified message");
        }

        messageRepository.delete(existingMessage);
    }

    @Override
    public Message markAsRead(Long id) {
        Message existingMessage = messageQueryService.getById(id);

        if (!conversationQueryService.isUserParticipantInConversation(UserUtil.getCurrentUser().getId(), existingMessage.getConversation().getId())){
            throw new UnauthorizedActionException("User is not a part of the conversation this message is in");
        }

        if (existingMessage.getReaders().contains(UserUtil.getCurrentUser())){
            throw new DuplicateResourceException("Message is already marked as read");
        }

        existingMessage.getReaders().add(UserUtil.getCurrentUser());

        return messageRepository.save(existingMessage);
    }

    private boolean isUserOwnerOfMessage(Message message){
        return Objects.equals(message.getAuthor().getId(), UserUtil.getCurrentUser().getId());
    }
}
