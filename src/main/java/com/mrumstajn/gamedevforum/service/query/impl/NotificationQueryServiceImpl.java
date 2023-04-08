package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequest;
import com.mrumstajn.gamedevforum.entity.Notification;
import com.mrumstajn.gamedevforum.repository.NotificationRepository;
import com.mrumstajn.gamedevforum.service.query.NotificationQueryService;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> search(SearchNotificationRequest request) {
        SearchConfiguration<Notification, Notification, SearchNotificationRequest> searchConfiguration =
                SearchConfiguration.<Notification, Notification, SearchNotificationRequest>builder()
                        .resolvePropertyMappingUsingPrefix(true)
                        .resultClass(Notification.class)
                        .build();

        return notificationRepository.findAll(request, searchConfiguration);
    }

    @Override
    public List<Notification> getAllByIds(List<Long> ids) {
        return notificationRepository.findAllById(ids);
    }
}
