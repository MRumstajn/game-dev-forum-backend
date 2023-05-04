package com.mrumstajn.gamedevforum.notification.service.command;

import com.mrumstajn.gamedevforum.notification.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.notification.dto.request.MarkNotificationsAsReadRequest;
import com.mrumstajn.gamedevforum.notification.entity.Notification;

import java.util.List;

public interface NotificationCommandService {

    List<Notification> createAll(List<CreateNotificationRequest> requests);

    void markAllAsRead(MarkNotificationsAsReadRequest request);
}
