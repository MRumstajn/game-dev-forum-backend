package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.EditUserPostReactionRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchUserPostReactionCountRequest;
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

        return ResponseEntity.created(URI.create("/post-reactions/" + reaction.getId())).body(modelMapper.map(reaction, UserPostReactionResponse.class));
    }

    @PostMapping("/counts")
    public ResponseEntity<List<PostReactionTypeCountResponse>> getCountsForAll(@RequestBody @Valid SearchUserPostReactionCountRequest request){
        return ResponseEntity.ok(reactionQueryService.getReactionCountForAll(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPostReactionResponse> edit(@PathVariable Long id, @RequestBody @Valid EditUserPostReactionRequest request){
        return ResponseEntity.ok(modelMapper.map(reactionCommandService.edit(id, request), UserPostReactionResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reactionCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
