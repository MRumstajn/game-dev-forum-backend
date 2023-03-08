package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.entity.Post;

public interface PostCommandService {

    Post create(CreatePostRequest request);
}
