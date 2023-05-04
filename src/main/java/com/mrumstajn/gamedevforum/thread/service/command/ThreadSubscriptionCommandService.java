package com.mrumstajn.gamedevforum.thread.service.command;

import com.mrumstajn.gamedevforum.thread.dto.request.SubscribeToThreadRequest;
import com.mrumstajn.gamedevforum.thread.dto.request.UnsubscribeFromThreadRequest;
import com.mrumstajn.gamedevforum.thread.entity.ThreadSubscription;

public interface ThreadSubscriptionCommandService {

    ThreadSubscription create(SubscribeToThreadRequest request);

    void delete(UnsubscribeFromThreadRequest request);
}
