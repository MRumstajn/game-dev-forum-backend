package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateForumUserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Size(max = 100)
    private String bio;
}
