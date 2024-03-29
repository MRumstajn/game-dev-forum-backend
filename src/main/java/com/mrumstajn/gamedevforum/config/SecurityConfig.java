package com.mrumstajn.gamedevforum.config;

import com.mrumstajn.gamedevforum.auth.service.query.AuthorizationQueryService;
import com.mrumstajn.gamedevforum.filter.JWTTokenFilter;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthorizationQueryService authorizationQueryService) throws Exception {

        return httpSecurity.csrf().disable()
                .addFilterAfter(new JWTTokenFilter(authorizationQueryService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/current-user").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/auth/change-password").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/statistics/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/statistics/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/posts/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/posts/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/posts/search/latest-activity").permitAll()
                .requestMatchers(HttpMethod.GET, "/posts/search/top/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/posts/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/posts/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/posts/total-posts/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/users/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/*/follow").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/*/unfollow").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/*/is-following").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/*/followers").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/top-reputation").permitAll()
                .requestMatchers(HttpMethod.GET, "/threads/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/threads/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/threads/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/threads/*/subscribe").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/threads/*/unsubscribe").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/threads").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/threads/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/categories/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/categories/*").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/categories/*").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/categories/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/sections").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/sections/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/sections/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/post-reactions").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/post-reactions/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/post-reactions/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/post-reactions/counts").permitAll()
                .requestMatchers(HttpMethod.POST, "/notifications/search").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/notifications/mark-as-read").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/notifications/unread-count").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/work-offers").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/work-offers/search").permitAll()
                .requestMatchers(HttpMethod.PUT, "/work-offers/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/work-offers/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/work-offers/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/work-offer-ratings/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/work-offer-ratings/average-and-total").permitAll()
                .requestMatchers(HttpMethod.POST, "/work-offer-ratings").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/work-offer-ratings/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/work-offer-ratings/*").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/work-offer-categories").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/work-offer-categories/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/work-offer-categories/*").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/work-offer-categories/*").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/work-offer-categories/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/messages").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/messages").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/messages").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/messages/search").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/messages/search/senders").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/messages/*/mark-as-read").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/conversations/search").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/conversations/*").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().build();
    }

}
