package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.entity.Notification;
import com.mrumstajn.gamedevforum.repository.NotificationRepository;
import com.mrumstajn.gamedevforum.service.query.NotificationQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public Long countUnreadForCurrentUser() {
        return notificationRepository.countAllByRecipientIdAndIsRead(UserUtil.getCurrentUser().getId(), false);
    }
}
