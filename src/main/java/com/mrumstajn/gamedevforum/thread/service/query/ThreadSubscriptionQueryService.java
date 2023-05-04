package com.mrumstajn.gamedevforum.thread.service.query;

import com.mrumstajn.gamedevforum.thread.dto.request.SearchThreadSubscriptionRequest;
import com.mrumstajn.gamedevforum.thread.entity.ThreadSubscription;

import java.util.List;

public interface ThreadSubscriptionQueryService {

    List<ThreadSubscription> search(SearchThreadSubscriptionRequest request);

    List<ThreadSubscription> getAllByThreadId(Long threadId);

    Boolean isUserSubscribedToThread(Long userId, Long threadId);
}
