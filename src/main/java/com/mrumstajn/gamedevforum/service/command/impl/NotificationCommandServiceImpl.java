package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.dto.request.MarkNotificationsAsReadRequest;
import com.mrumstajn.gamedevforum.entity.Notification;
import com.mrumstajn.gamedevforum.repository.NotificationRepository;
import com.mrumstajn.gamedevforum.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.service.query.NotificationQueryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;

    private final NotificationQueryService notificationQueryService;

    private final ModelMapper modelMapper;

    @Override
    public Notification create(CreateNotificationRequest request) {
        Notification notification = modelMapper.map(request, Notification.class);
        notification.setCreationDate(LocalDate.now());

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> markAllAsRead(MarkNotificationsAsReadRequest request) {
        List<Notification> notifications = notificationQueryService.getAllByIds(request.getNotificationIds());
        notifications.forEach(notification -> notification.setIsRead(true));

        return notificationRepository.saveAll(notifications);
    }
}