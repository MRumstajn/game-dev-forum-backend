package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequest;
import com.mrumstajn.gamedevforum.entity.Notification;

import java.util.List;

public interface NotificationQueryService {

    List<Notification> search(SearchNotificationRequest request);

    List<Notification> getAllByIds(List<Long> ids);
}
