package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateNotificationRequest;
import com.mrumstajn.gamedevforum.dto.request.MarkNotificationsAsReadRequest;
import com.mrumstajn.gamedevforum.entity.Notification;

import java.util.List;

public interface NotificationCommandService {

    List<Notification> createAll(List<CreateNotificationRequest> requests);

    List<Notification> markAllAsRead(MarkNotificationsAsReadRequest request);
}
