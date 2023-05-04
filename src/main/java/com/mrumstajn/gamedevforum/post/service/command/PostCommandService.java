package com.mrumstajn.gamedevforum.post.service.command;

import com.mrumstajn.gamedevforum.post.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.post.entity.Post;

public interface PostCommandService {

    Post create(CreatePostRequest request);

    Post edit(Long id, EditPostRequest request);

    void delete(Long id);
}
