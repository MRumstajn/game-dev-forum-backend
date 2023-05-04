package com.mrumstajn.gamedevforum.auth.service.dto.response;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;

    private ForumUserResponse user;
}
