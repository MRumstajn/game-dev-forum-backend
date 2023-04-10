package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Notification;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationQueryService {

    Page<Notification> search(SearchNotificationRequestPageable request);

    List<Notification> getAllByIds(List<Long> ids);
}
