package com.mrumstajn.gamedevforum.thread.service.command.impl;

import com.mrumstajn.gamedevforum.thread.dto.request.SubscribeToThreadRequest;
import com.mrumstajn.gamedevforum.thread.dto.request.UnsubscribeFromThreadRequest;
import com.mrumstajn.gamedevforum.thread.entity.ThreadSubscription;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.thread.repository.ThreadSubscriptionRepository;
import com.mrumstajn.gamedevforum.thread.service.command.ThreadSubscriptionCommandService;
import com.mrumstajn.gamedevforum.thread.service.query.ForumThreadQueryService;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.thread.service.query.ThreadSubscriptionQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ThreadSubscriptionCommandServiceImpl implements ThreadSubscriptionCommandService {
   private final ThreadSubscriptionRepository threadSubscriptionRepository;

   private final ThreadSubscriptionQueryService threadSubscriptionQueryService;

   private final ForumUserQueryService forumUserQueryService;

   private final ForumThreadQueryService forumThreadQueryService;

    @Override
    public ThreadSubscription create(SubscribeToThreadRequest request) {
        if (threadSubscriptionQueryService.isUserSubscribedToThread(UserUtil.getCurrentUser().getId(), request.getThreadId())){
            throw new DuplicateResourceException("User is already subscribed to the specified thread");
        }

        ThreadSubscription newSubscription = new ThreadSubscription();
        newSubscription.setUser(forumUserQueryService.getById(UserUtil.getCurrentUser().getId()));
        newSubscription.setThread(forumThreadQueryService.getById(request.getThreadId()));

        return threadSubscriptionRepository.save(newSubscription);
    }

    @Override
    public void delete(UnsubscribeFromThreadRequest request) {
        ThreadSubscription existingSubscription = threadSubscriptionRepository
                .findByUserIdAndThreadId(UserUtil.getCurrentUser().getId(), request.getThreadId())
                .orElseThrow(() -> new EntityNotFoundException("User is not subscribed to the specified thread"));

        if (!Objects.equals(UserUtil.getCurrentUser().getId(), existingSubscription.getUser().getId())){
            throw new UnauthorizedActionException("User is not the owner of this resource");
        }

        threadSubscriptionRepository.delete(existingSubscription);
    }
}
