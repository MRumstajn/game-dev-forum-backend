package com.mrumstajn.gamedevforum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchPostRequest;
import com.mrumstajn.gamedevforum.dto.response.PostResponse;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostQueryService postQueryService;

    private final PostCommandService postCommandService;

    private final ObjectMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(mapper.convertValue(postQueryService.getById(id), PostResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<List<PostResponse>> search(@RequestBody @Valid SearchPostRequest request){
        return ResponseEntity.ok(postQueryService.search(request).stream()
                .map(post -> mapper.convertValue(post, PostResponse.class)).toList());
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody @Valid CreatePostRequest request){
        Post newPost = postCommandService.create(request);

        return ResponseEntity.created(URI.create("/posts/" + newPost.getId()))
                .body(mapper.convertValue(newPost, PostResponse.class));
    }
}
