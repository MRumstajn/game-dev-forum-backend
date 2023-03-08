package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateForumUserRequest {
    @NotBlank
    private String username;
}
