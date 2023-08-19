package com.mrumstajn.gamedevforum.notification.service.command.impl;

import com.mrumstajn.gamedevforum.notification.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.notification.dto.request.MarkNotificationsAsReadRequest;
import com.mrumstajn.gamedevforum.notification.entity.Notification;
import com.mrumstajn.gamedevforum.notification.repository.NotificationRepository;
import com.mrumstajn.gamedevforum.notification.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;

    private final ModelMapper modelMapper;

    private final ForumUserQueryService forumUserQueryService;

    @Override
    @Transactional
    public List<Notification> createAll(List<CreateNotificationRequest> requests) {
        List<Notification> createdNotifications = new ArrayList<>();

        requests.forEach(request -> {
            Notification notification = modelMapper.map(request, Notification.class);
            notification.setCreationDate(LocalDate.now());
            notification.setRecipient(forumUserQueryService.getById(request.getRecipientIdentifier()));

            createdNotifications.add(notification);
        });


        return notificationRepository.saveAll(createdNotifications);
    }

    @Override
    @Transactional
    public void markAllAsRead(MarkNotificationsAsReadRequest request) {
        if (request.getMarkAllAsRead() != null && request.getMarkAllAsRead()){
            notificationRepository.markAllAsReadById(UserUtil.getCurrentUser().getId(), List.of(), true);
        } else {
            notificationRepository.markAllAsReadById(UserUtil.getCurrentUser().getId(), request.getNotificationIds(), false);
        }
    }
}
