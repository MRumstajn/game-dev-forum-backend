package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.ChangeForumUserPasswordRequest;
import com.mrumstajn.gamedevforum.dto.request.LoginRequest;
import com.mrumstajn.gamedevforum.dto.response.EditForumUserResponse;
import com.mrumstajn.gamedevforum.dto.response.ForumUserResponse;
import com.mrumstajn.gamedevforum.dto.response.LoginResponse;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.exception.LoginException;
import com.mrumstajn.gamedevforum.service.command.ForumUserCommandService;
import com.mrumstajn.gamedevforum.service.command.JWTCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final ForumUserQueryService forumUserQueryService;

    private final ForumUserCommandService forumUserCommandService;

    private final JWTCommandService jwtCommandService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        // check if username exists in db
        ForumUser user = forumUserQueryService.getByUsernameExact(request.getUsername());
        if (user == null){
            throw new LoginException("Invalid username or password");
        }

        // check password against stored hash
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new LoginException("Invalid username or password");
        }

        // if ok, generate and return token
        String token = jwtCommandService.generateTokenForUser(user);

        return ResponseEntity.ok(new LoginResponse(token, modelMapper.map(user, ForumUserResponse.class)));
    }

    @GetMapping("/current-user")
    public ResponseEntity<ForumUserResponse> currentUser(){
        return ResponseEntity.ok(modelMapper.map(UserUtil.getCurrentUser(), ForumUserResponse.class));
    }

    @PostMapping("/change-password")
    public ResponseEntity<EditForumUserResponse> changePassword(@RequestBody @Valid ChangeForumUserPasswordRequest request){
        Long userId = request.getUserId();

        ForumUser existingUser = forumUserQueryService.getById(userId);

        EditForumUserResponse response = new EditForumUserResponse();
        response.setUser(modelMapper.map(forumUserCommandService.changePassword(userId, request), ForumUserResponse.class));
        response.setNewAccessToken(jwtCommandService.generateTokenForUser(existingUser));

        return ResponseEntity.ok(response);
    }
}
