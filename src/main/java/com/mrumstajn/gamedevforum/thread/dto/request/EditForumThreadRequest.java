package com.mrumstajn.gamedevforum.thread.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditForumThreadRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String title;
}
