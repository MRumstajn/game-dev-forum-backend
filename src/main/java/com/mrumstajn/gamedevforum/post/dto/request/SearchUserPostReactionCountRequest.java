package com.mrumstajn.gamedevforum.post.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchUserPostReactionCountRequest {
    @NotEmpty
    private List<Long> postIds;
}
