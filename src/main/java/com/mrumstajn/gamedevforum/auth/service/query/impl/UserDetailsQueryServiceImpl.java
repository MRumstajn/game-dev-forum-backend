package com.mrumstajn.gamedevforum.auth.service.query.impl;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.auth.service.query.UserDetailsQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsQueryServiceImpl implements UserDetailsQueryService {
    private final ForumUserQueryService forumUserQueryService;

    @Override
    public UserDetails getByUsername(String username) {
        ForumUser forumUser = forumUserQueryService.getByUsernameExact(username);

        return new User(forumUser.getUsername(), forumUser.getPasswordHash(), List.of(
                new SimpleGrantedAuthority(forumUser.getRole().name())
        ));
    }
}
