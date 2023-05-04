package com.mrumstajn.gamedevforum.thread.repository;

import com.mrumstajn.gamedevforum.thread.entity.ThreadSubscription;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThreadSubscriptionRepository extends JpaRepository<ThreadSubscription, Long>, SearchExecutor<ThreadSubscription> {

    Optional<ThreadSubscription> findByUserIdAndThreadId(Long userId, Long threadId);

    List<ThreadSubscription> findAllByThreadId(Long threadId);

    Boolean existsByUserIdAndThreadId(Long userId, Long threadId);
}
