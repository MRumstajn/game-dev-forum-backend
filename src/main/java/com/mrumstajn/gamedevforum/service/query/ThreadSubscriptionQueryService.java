package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchThreadSubscriptionRequest;
import com.mrumstajn.gamedevforum.entity.ThreadSubscription;

import java.util.List;

public interface ThreadSubscriptionQueryService {

    List<ThreadSubscription> search(SearchThreadSubscriptionRequest request);

    List<ThreadSubscription> getAllByThreadId(Long threadId);

    Boolean isUserSubscribedToThread(Long userId, Long threadId);
}
