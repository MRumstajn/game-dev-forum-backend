package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.ThreadSubscription;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThreadSubscriptionRepository extends JpaRepository<ThreadSubscription, Long>, SearchExecutor<ThreadSubscription> {

    Optional<ThreadSubscription> findByUserIdAndThreadId(Long userId, Long threadId);

    List<ThreadSubscription> findAllByThreadId(Long threadId);

    Boolean existsByUserIdAndThreadId(Long userId, Long threadId);
}
