package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.SubscribeToThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.UnsubscribeFromThreadRequest;
import com.mrumstajn.gamedevforum.entity.ThreadSubscription;

public interface ThreadSubscriptionCommandService {

    ThreadSubscription create(SubscribeToThreadRequest request);

    void delete(UnsubscribeFromThreadRequest request);
}
