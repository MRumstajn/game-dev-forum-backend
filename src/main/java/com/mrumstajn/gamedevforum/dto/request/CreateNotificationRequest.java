package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {
    @NotNull
    private Long recipientId;

    @NotNull
    @Size(max = 50)
    private String title;

    @NotNull
    @Size(max = 100)
    private String content;
}
