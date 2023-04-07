package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditForumUserRequest {
    @Size(min = 3)
    private String username;

    @Size(max = 100)
    private String bio;
}
