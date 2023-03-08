package com.mrumstajn.gamedevforum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ForumUserResponse {
    private Long id;

    private String username;

    private LocalDate joinDate;
}
