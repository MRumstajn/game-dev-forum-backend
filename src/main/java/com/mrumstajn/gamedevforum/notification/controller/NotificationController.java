package com.mrumstajn.gamedevforum.notification.controller;

import com.mrumstajn.gamedevforum.notification.dto.request.MarkNotificationsAsReadRequest;
import com.mrumstajn.gamedevforum.notification.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.notification.dto.response.UnreadNotificationCountResponse;
import com.mrumstajn.gamedevforum.notification.dto.response.NotificationResponse;
import com.mrumstajn.gamedevforum.notification.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.notification.service.query.NotificationQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationQueryService notificationQueryService;

    private final NotificationCommandService notificationCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/unread-count")
    public ResponseEntity<UnreadNotificationCountResponse> getCountInfo(){
        UnreadNotificationCountResponse response = new UnreadNotificationCountResponse();
        response.setUnreadNotifications(notificationQueryService.countUnreadForCurrentUser());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<NotificationResponse>> search(@RequestBody @Valid SearchNotificationRequestPageable request){
        return ResponseEntity.ok(notificationQueryService.search(request)
                .map(notification -> modelMapper.map(notification, NotificationResponse.class)));
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<Void> markAllAsRead(@RequestBody @Valid MarkNotificationsAsReadRequest request) {
        notificationCommandService.markAllAsRead(request);

        return ResponseEntity.noContent().build();
    }
}
