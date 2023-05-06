package com.mrumstajn.gamedevforum.filter;

import com.mrumstajn.gamedevforum.auth.service.query.AuthorizationQueryService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {
    private final AuthorizationQueryService authorizationQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authenticationToken = authorizationQueryService.getAuthenticationFromJWTToken(request.getHeader("Authorization"));
        if (authenticationToken != null){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
