package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MarkNotificationsAsReadRequest {
    @NotEmpty
    private List<Long> notificationIds;
}
