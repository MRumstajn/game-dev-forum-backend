package com.mrumstajn.gamedevforum.user.dto.response;

import com.mrumstajn.gamedevforum.user.entity.ForumUserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ForumUserResponse {
    private Long id;

    private String username;

    private LocalDate joinDate;

    private ForumUserRole role;

    private String bio;

    private Long reputation;
}
