package com.mrumstajn.gamedevforum.notification.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.validation.api.constraint.NotNullWhen;

import java.util.List;
import java.util.function.Predicate;

@Getter
@Setter
@NotNullWhen(property = "notificationIds", condition = MarkNotificationsAsReadRequest.MarkAllBooleanNull.class)
public class MarkNotificationsAsReadRequest {
    @Size(min = 1)
    private List<Long> notificationIds;

    private Boolean markAllAsRead;

    public static class MarkAllBooleanNull implements Predicate<MarkNotificationsAsReadRequest>{

        @Override
        public boolean test(MarkNotificationsAsReadRequest request) {
            return request.getMarkAllAsRead() == null;
        }
    }
}
