package com.mrumstajn.gamedevforum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.response.ForumThreadResponse;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class ForumThreadController {
    private final ForumThreadQueryService forumThreadQueryService;

    private final ForumThreadCommandService forumThreadCommandService;

    private final ObjectMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.convertValue(forumThreadQueryService.getById(id), ForumThreadResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<List<ForumThreadResponse>> search(@RequestBody @Valid SearchForumThreadRequest request) {
        return ResponseEntity.ok(forumThreadQueryService.search(request).stream()
                .map(thread -> mapper.convertValue(thread, ForumThreadResponse.class)).toList());
    }

    @PostMapping
    public ResponseEntity<ForumThreadResponse> create(@RequestBody @Valid CreateForumThreadRequest request) {
        ForumThread newThread = forumThreadCommandService.create(request);

        return ResponseEntity.created(URI.create("/threads/" + newThread.getId()))
                .body(mapper.convertValue(newThread, ForumThreadResponse.class));
    }
}