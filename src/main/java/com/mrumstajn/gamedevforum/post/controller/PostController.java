package com.mrumstajn.gamedevforum.post.controller;

import com.mrumstajn.gamedevforum.post.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchThreadForPostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchPostRequestPageable;
import com.mrumstajn.gamedevforum.post.dto.response.PostResponse;
import com.mrumstajn.gamedevforum.post.entity.Post;
import com.mrumstajn.gamedevforum.post.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.post.service.query.PostQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostQueryService postQueryService;

    private final PostCommandService postCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(modelMapper.map(postQueryService.getById(id), PostResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PostResponse>> search(@RequestBody @Valid SearchPostRequestPageable request){
        return ResponseEntity.ok(postQueryService.search(request).map(post -> modelMapper.map(post, PostResponse.class)));
    }

    @PostMapping("/search/latest-activity")
    public ResponseEntity<PostResponse> searchByLatestActivity(@RequestBody @Valid SearchThreadForPostRequest request){
        Post latest = postQueryService.getLatest(request);
        if (latest == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(modelMapper.map(latest, PostResponse.class));
    }

    @GetMapping("/search/top/{threadId}")
    public ResponseEntity<PostResponse> searchTopPostByLikesInThread(@PathVariable Long threadId) {
        Post topPostByLikes = postQueryService.getTopByLikesInThread(threadId);
        if (topPostByLikes == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(modelMapper.map(topPostByLikes, PostResponse.class));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody @Valid CreatePostRequest request){
        Post newPost = postCommandService.create(request);

        return ResponseEntity.created(URI.create("/posts/" + newPost.getId()))
                .body(modelMapper.map(newPost, PostResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> edit(@PathVariable Long id, @RequestBody @Valid EditPostRequest request){
        return ResponseEntity.ok(modelMapper.map(postCommandService.edit(id ,request), PostResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        postCommandService.delete(id);

        return ResponseEntity.ok().build();
    }
}
