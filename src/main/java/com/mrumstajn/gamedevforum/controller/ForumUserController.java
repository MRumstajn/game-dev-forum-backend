package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.dto.response.ForumUserResponse;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.service.command.ForumUserCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ForumUserController {
    private final ForumUserQueryService forumUserQueryService;

    private final ForumUserCommandService forumUserCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ForumUserResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(modelMapper.map(forumUserQueryService.getById(id), ForumUserResponse.class));
    }

    @PostMapping
    public ResponseEntity<ForumUserResponse> create(@RequestBody @Valid CreateForumUserRequest request){
        ForumUser newUser = forumUserCommandService.create(request);

        return ResponseEntity.created(URI.create("/users/" + newUser.getId()))
                .body(modelMapper.map(newUser, ForumUserResponse.class));
    }
}
