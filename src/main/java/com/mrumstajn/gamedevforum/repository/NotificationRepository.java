package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Notification;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, SearchExecutor<Notification> {
}
