package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditForumUserResponse {
    private ForumUserResponse user;

    private String newAccessToken;
}
