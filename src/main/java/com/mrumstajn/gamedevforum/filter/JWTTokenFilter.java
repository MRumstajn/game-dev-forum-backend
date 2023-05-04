package com.mrumstajn.gamedevforum.filter;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
            boolean authorized = false;

            String token = request.getHeader("Authorization");
            if (token != null && token.contains("Bearer")) {
                token = token.replace("Bearer", "");

                if (token.length() > 0){
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
                        authorized = true;

                    }
                }

            }

            if (!authorized || SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            sendUnauthorizedResponse(response);
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
