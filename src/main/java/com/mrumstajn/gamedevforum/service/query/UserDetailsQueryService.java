package com.mrumstajn.gamedevforum.service.query;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsQueryService {

    UserDetails getByUsername(String username);
}
