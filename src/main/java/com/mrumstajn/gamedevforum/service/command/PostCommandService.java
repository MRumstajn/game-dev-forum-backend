package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.dto.request.EditPostRequest;
import com.mrumstajn.gamedevforum.entity.Post;

public interface PostCommandService {

    Post create(CreatePostRequest request);

    Post edit(Long id, EditPostRequest request);

    void delete(Long id);

    Post increaseLikes(Long id);

    Post increaseDislikes(Long id);
}
