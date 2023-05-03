package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Notification;
import org.springframework.data.domain.Page;

public interface NotificationQueryService {

    Page<Notification> search(SearchNotificationRequestPageable request);

    Long countUnreadForCurrentUser();
}
