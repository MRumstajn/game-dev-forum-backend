package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPostRequest {
    @Size(min = 1)
    private String content;
}
