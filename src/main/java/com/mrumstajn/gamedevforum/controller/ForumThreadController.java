package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchLatestForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.response.ForumThreadResponse;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ForumThreadResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(forumThreadQueryService.getById(id), ForumThreadResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<List<ForumThreadResponse>> search(@RequestBody @Valid SearchForumThreadRequest request) {
        return ResponseEntity.ok(forumThreadQueryService.search(request).stream()
                .map(thread -> modelMapper.map(thread, ForumThreadResponse.class)).toList());
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
}