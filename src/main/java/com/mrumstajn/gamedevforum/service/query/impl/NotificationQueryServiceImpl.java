package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Notification;
import com.mrumstajn.gamedevforum.repository.NotificationRepository;
import com.mrumstajn.gamedevforum.service.query.NotificationQueryService;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    @Override
    public Page<Notification> search(SearchNotificationRequestPageable request) {
        SearchConfiguration<Notification, Notification, SearchNotificationRequestPageable> searchConfiguration =
                SearchConfiguration.<Notification, Notification, SearchNotificationRequestPageable>builder()
                        .resolvePropertyMappingUsingPrefix(true)
                        .resultClass(Notification.class)
                        .build();

        return notificationRepository.findAll(request, searchConfiguration, PageableUtil.convertToPageable(request));
    }

    @Override
    public List<Notification> getAllByIds(List<Long> ids) {
        return notificationRepository.findAllById(ids);
    }
}
