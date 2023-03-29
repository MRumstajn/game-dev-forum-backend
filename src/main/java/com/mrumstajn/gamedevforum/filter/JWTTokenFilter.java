package com.mrumstajn.gamedevforum.filter;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {

    private final ForumUserQueryService forumUserQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtSecret = System.getenv("jwtsecret");
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.contains("Bearer")) {
                token = token.replace("Bearer", "");

                if (token.length() == 0){
                    SecurityContextHolder.clearContext();
                    return;
                }

                Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes())
                        .parseClaimsJws(token).getBody();

                if (claims.get("authorities") != null) {
                    List<String> roles = (List<String>) claims.get("authorities");
                    ForumUser user = forumUserQueryService.getByUsernameExact(claims.getSubject());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
                            null, roles
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
