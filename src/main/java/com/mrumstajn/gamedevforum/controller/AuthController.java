package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.LoginRequest;
import com.mrumstajn.gamedevforum.dto.response.ForumUserResponse;
import com.mrumstajn.gamedevforum.dto.response.LoginResponse;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.exception.LoginException;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    public static final int JWT_DURATION_IN_SECONDS = 100;

    private final ForumUserQueryService forumUserQueryService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){

        // TODO add role column or table and apply roles read from db into token

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
        String token =
                Jwts.builder().setId("gamedevforum")
                .setSubject(user.getUsername())
                        .claim("id", user.getId())
                        .claim("authorities",
                        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER").stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusSeconds(JWT_DURATION_IN_SECONDS)))
                .signWith(SignatureAlgorithm.HS256, System.getenv("jwtsecret").getBytes()).compact();

        return ResponseEntity.ok(new LoginResponse(token, modelMapper.map(user, ForumUserResponse.class)));

    }

    @GetMapping("/current-user")
    public ResponseEntity<ForumUserResponse> currentUser(){
        ForumUser currentUser = (ForumUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(modelMapper.map(currentUser, ForumUserResponse.class));
    }
}
