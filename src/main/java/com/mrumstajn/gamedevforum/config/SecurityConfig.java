package com.mrumstajn.gamedevforum.config;

import com.mrumstajn.gamedevforum.filter.JWTTokenFilter;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, ForumUserQueryService forumUserQueryService) throws Exception {
        return httpSecurity.csrf().disable()
                .addFilterAfter(new JWTTokenFilter(forumUserQueryService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/current-user").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/change-password").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/statistics/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/statistics/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/posts/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/posts/search").permitAll()
                .requestMatchers(HttpMethod.PUT, "/posts/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/posts/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/users/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/*/follow").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/*/unfollow").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/*/is-following").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/*/followers").permitAll()
                .requestMatchers(HttpMethod.GET, "/threads/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/threads").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/threads/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/categories/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/categories/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/sections").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/sections/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/sections/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/post-reactions").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/post-reactions/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/post-reactions/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/post-reactions/counts").permitAll()
                .anyRequest().authenticated()
                .and().build();
    }

}
