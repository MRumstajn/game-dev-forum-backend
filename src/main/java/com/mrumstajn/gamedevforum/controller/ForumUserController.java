package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.dto.request.EditForumUserRequest;
import com.mrumstajn.gamedevforum.dto.response.CheckIsFollowingResponse;
import com.mrumstajn.gamedevforum.dto.response.EditForumUserResponse;
import com.mrumstajn.gamedevforum.dto.response.ForumUserResponse;
import com.mrumstajn.gamedevforum.dto.response.UserFollowerResponse;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.service.command.ForumUserCommandService;
import com.mrumstajn.gamedevforum.service.command.JWTCommandService;
import com.mrumstajn.gamedevforum.service.command.UserFollowerCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.service.query.UserFollowerQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ForumUserController {
    private final ForumUserQueryService forumUserQueryService;

    private final ForumUserCommandService forumUserCommandService;

    private final JWTCommandService jwtCommandService;

    private final UserFollowerCommandService userFollowerCommandService;

    private final UserFollowerQueryService userFollowerQueryService;

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

    @PutMapping("/{id}")
    public ResponseEntity<EditForumUserResponse> edit(@PathVariable Long id, @RequestBody @Valid EditForumUserRequest request){
        ForumUser existingUser = forumUserQueryService.getById(id);

        EditForumUserResponse response = new EditForumUserResponse();
        response.setUser(modelMapper.map(forumUserCommandService.edit(id, request), ForumUserResponse.class));
        response.setNewAccessToken(jwtCommandService.generateTokenForUser(existingUser));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<UserFollowerResponse> follow(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(userFollowerCommandService.create(id), UserFollowerResponse.class));
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<Void> unfollow(@PathVariable Long id) {
        userFollowerCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<ForumUserResponse>> getFollowers(@PathVariable Long id){
        return ResponseEntity.ok(userFollowerQueryService.getFollowersByFollowedUserId(id).stream()
                .map(follower -> modelMapper.map(follower, ForumUserResponse.class)).toList());
    }

    @GetMapping("/{id}/is-following")
    public ResponseEntity<CheckIsFollowingResponse> checkIsFollowing(@PathVariable Long id){
        CheckIsFollowingResponse response = new CheckIsFollowingResponse();
        response.setUserId(UserUtil.getCurrentUser().getId());
        response.setTargetUserId(id);
        response.setIsFollowing(userFollowerQueryService.isUserFollowingUser(id));

        return ResponseEntity.ok(response);
    }
}
