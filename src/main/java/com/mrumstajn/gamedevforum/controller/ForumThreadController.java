package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.*;
import com.mrumstajn.gamedevforum.dto.response.ForumThreadResponse;
import com.mrumstajn.gamedevforum.dto.response.ThreadSubscriptionResponse;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.command.ThreadSubscriptionCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class ForumThreadController {
    private final ForumThreadQueryService forumThreadQueryService;

    private final ForumThreadCommandService forumThreadCommandService;

    private final ThreadSubscriptionCommandService threadSubscriptionCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(forumThreadQueryService.getById(id), ForumThreadResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ForumThreadResponse>> search(@RequestBody @Valid SearchForumThreadRequestPageable request) {
        return ResponseEntity.ok(forumThreadQueryService.search(request).map(thread -> modelMapper.map(thread, ForumThreadResponse.class)));
    }

    @PostMapping("/search/latest-activity")
    public ResponseEntity<ForumThreadResponse> searchByLatestActivity(@RequestBody @Valid SearchLatestForumThreadRequest request) {
        ForumThread latest = forumThreadQueryService.getLatest(request);
        if (latest == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(modelMapper.map(latest, ForumThreadResponse.class));
    }

    @PostMapping
    public ResponseEntity<ForumThreadResponse> create(@RequestBody @Valid CreateForumThreadRequest request) {
        ForumThread newThread = forumThreadCommandService.create(request);

        return ResponseEntity.created(URI.create("/threads/" + newThread.getId()))
                .body(modelMapper.map(newThread, ForumThreadResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumThreadResponse> edit(@PathVariable Long id, @RequestBody @Valid EditForumThreadRequest request){
        return ResponseEntity.ok(modelMapper.map(forumThreadCommandService.edit(id, request), ForumThreadResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        forumThreadCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<ThreadSubscriptionResponse> subscribe(@PathVariable Long id){
        return ResponseEntity.ok(modelMapper.map(threadSubscriptionCommandService
                .create(new SubscribeToThreadRequest(id)), ThreadSubscriptionResponse.class));
    }

    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long id){
        threadSubscriptionCommandService.delete(new UnsubscribeFromThreadRequest(id));

        return ResponseEntity.noContent().build();
    }
}