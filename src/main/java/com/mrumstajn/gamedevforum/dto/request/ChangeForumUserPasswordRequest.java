package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeForumUserPasswordRequest {
    private Long userId;

    private String currentPassword;

    private String newPassword;
}
