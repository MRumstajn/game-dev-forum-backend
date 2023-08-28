package com.mrumstajn.gamedevforum.auth.service.command.impl;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.auth.service.command.JWTCommandService;
import com.mrumstajn.gamedevforum.auth.service.query.UserDetailsQueryService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTCommandServiceImpl implements JWTCommandService {
    @Value("${jwt.expiration-seconds}")
    public int jwtExpirationInSeconds = 60000;

    private final UserDetailsQueryService userDetailsQueryService;

    @SuppressWarnings("deprecation")
    @Override
    public String generateTokenForUser(ForumUser user) {
        UserDetails userDetails = userDetailsQueryService.getByUsername(user.getUsername());

        return Jwts.builder().setId("gamedevforum")
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("authorities",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                //.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusSeconds(jwtExpirationInSeconds)))
                .signWith(SignatureAlgorithm.HS256, System.getProperty("jwtsecret").getBytes()).compact();
    }
}
