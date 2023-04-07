package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.dto.response.UserPostReactionResponse;
import com.mrumstajn.gamedevforum.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.service.command.UserPostReactionCommandService;
import com.mrumstajn.gamedevforum.service.query.UserPostReactionQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post-reactions")
public class UserPostReactionController {
    private final UserPostReactionQueryService reactionQueryService;

    private final UserPostReactionCommandService reactionCommandService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserPostReactionResponse> create(@RequestBody @Valid CreateUserPostReactionRequest request) {
        UserPostReaction reaction = reactionCommandService.create(request);

        SearchUserPostReactionCountRequest searchUserPostReactionCountRequest = new SearchUserPostReactionCountRequest();
        searchUserPostReactionCountRequest.setPostIds(List.of(request.getPostIdentifier()));

        UserPostReactionResponse response = modelMapper.map(reaction, UserPostReactionResponse.class);
        response.setReactionTypesCount(reactionQueryService.getReactionCountForAll(searchUserPostReactionCountRequest));

        return ResponseEntity.created(URI.create("/post-reactions/" + reaction.getId())).body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserPostReactionResponse>> search(@RequestBody @Valid SearchUserPostReactionRequest request){
        return ResponseEntity.ok(reactionQueryService.search(request).stream()
                .map(postReaction -> modelMapper.map(postReaction, UserPostReactionResponse.class)).toList());
    }

    @PostMapping("/counts")
    public ResponseEntity<List<PostReactionTypeCountResponse>> getCountsForAll(@RequestBody @Valid SearchUserPostReactionCountRequest request) {
        return ResponseEntity.ok(reactionQueryService.getReactionCountForAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPostReactionResponse> edit(@PathVariable Long id, @RequestBody @Valid EditUserPostReactionRequest request) {
        UserPostReactionResponse response = modelMapper.map(reactionCommandService.edit(id, request), UserPostReactionResponse.class);

        SearchUserPostReactionCountRequest searchUserPostReactionCountRequest = new SearchUserPostReactionCountRequest();
        searchUserPostReactionCountRequest.setPostIds(List.of(response.getPostId()));

        response.setReactionTypesCount(reactionQueryService.getReactionCountForAll(searchUserPostReactionCountRequest));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<PostReactionTypeCountResponse>> delete(@PathVariable Long id){
        UserPostReaction userPostReaction = reactionQueryService.getById(id);

        reactionCommandService.delete(id);

        SearchUserPostReactionCountRequest searchUserPostReactionCountRequest = new SearchUserPostReactionCountRequest();
        searchUserPostReactionCountRequest.setPostIds(List.of(userPostReaction.getPostId()));

        return ResponseEntity.ok(reactionQueryService.getReactionCountForAll(searchUserPostReactionCountRequest));
    }
}
