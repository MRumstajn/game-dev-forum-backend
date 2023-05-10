package com.mrumstajn.gamedevforum.auth.service.query.impl;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.auth.service.query.AuthorizationQueryService;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationQueryServiceImpl implements AuthorizationQueryService {

    private final ForumUserQueryService forumUserQueryService;

    @Override
    public ForumUser getCurrentUser() {
        return ((ForumUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationFromJWTToken(String token) {
        UsernamePasswordAuthenticationToken authenticationToken = null;

        String jwtSecret = System.getenv("jwtsecret");
        try {
            if (token != null) {
                if (token.contains("Bearer")) {
                    token = token.replace("Bearer", "");
                }

                if (token.length() > 0){
                    Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes())
                            .parseClaimsJws(token).getBody();

                    if (claims.get("authorities") != null) {
                        List<String> roles = (List<String>) claims.get("authorities");
                        ForumUser user = forumUserQueryService.getByUsernameExact(claims.getSubject());
                        authenticationToken = new UsernamePasswordAuthenticationToken(user,
                                null, roles
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList());
                    }
                }

            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException ignored) {
            return null;
        }

        return authenticationToken;
    }
}
