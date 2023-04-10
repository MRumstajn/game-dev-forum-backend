package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.MarkNotificationsAsReadRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchNotificationRequestPageable;
import com.mrumstajn.gamedevforum.dto.response.NotificationResponse;
import com.mrumstajn.gamedevforum.service.command.NotificationCommandService;
import com.mrumstajn.gamedevforum.service.query.NotificationQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationQueryService notificationQueryService;

    private final NotificationCommandService notificationCommandService;

    private final ModelMapper modelMapper;

    @PostMapping("/search")
    public ResponseEntity<Page<NotificationResponse>> search(@RequestBody @Valid SearchNotificationRequestPageable request){
        return ResponseEntity.ok(notificationQueryService.search(request)
                .map(notification -> modelMapper.map(notification, NotificationResponse.class)));
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<List<NotificationResponse>> markAllAsRead(@RequestBody @Valid MarkNotificationsAsReadRequest request) {
        return ResponseEntity.ok(notificationCommandService.markAllAsRead(request).stream()
                .map(notification -> modelMapper.map(notification, NotificationResponse.class)).toList());
    }
}
