package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchThreadSubscriptionRequest;
import com.mrumstajn.gamedevforum.entity.ThreadSubscription;
import com.mrumstajn.gamedevforum.repository.ThreadSubscriptionRepository;
import com.mrumstajn.gamedevforum.service.query.ThreadSubscriptionQueryService;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadSubscriptionQueryServiceImpl implements ThreadSubscriptionQueryService {
    private final ThreadSubscriptionRepository threadSubscriptionRepository;

    @Override
    public List<ThreadSubscription> search(SearchThreadSubscriptionRequest request) {
        SearchConfiguration<ThreadSubscription, ThreadSubscription, SearchThreadSubscriptionRequest> searchConfiguration =
                SearchConfiguration.<ThreadSubscription, ThreadSubscription, SearchThreadSubscriptionRequest>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(ThreadSubscription.class)
                .build();

        return threadSubscriptionRepository.findAll(request, searchConfiguration);
    }

    @Override
    public List<ThreadSubscription> getAllByThreadId(Long threadId) {
        return threadSubscriptionRepository.findAllByThreadId(threadId);
    }

    @Override
    public Boolean isUserSubscribedToThread(Long userId, Long threadId) {
        return threadSubscriptionRepository.existsByUserIdAndThreadId(userId, threadId);
    }
}
