package com.mrumstajn.gamedevforum.notification.service.query;

import com.mrumstajn.gamedevforum.notification.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.notification.entity.Notification;
import org.springframework.data.domain.Page;

public interface NotificationQueryService {

    Page<Notification> search(SearchNotificationRequestPageable request);

    Long countUnreadForCurrentUser();
}
