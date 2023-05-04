package com.mrumstajn.gamedevforum.notification.repository;

import com.mrumstajn.gamedevforum.notification.entity.Notification;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, SearchExecutor<Notification> {
    Long countAllByRecipientIdAndIsRead(Long recipientId, Boolean isRead);

    @Modifying
    @Query(
            """
                            UPDATE Notification n SET n.isRead = true WHERE n.recipient.id = :recipientId
                            AND (:markAllAsRead = true OR n.id IN :notificationIds)
                    """
    )
    void markAllAsReadById(@Param("recipientId") Long recipientId, @Param("notificationIds") List<Long> notificationIds,
                           @Param("markAllAsRead") Boolean markAllAsRead);
}
