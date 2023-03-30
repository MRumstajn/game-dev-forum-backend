package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.repository.ForumThreadRepository;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumThreadCommandServiceImpl implements ForumThreadCommandService {
    private final ForumThreadRepository forumThreadRepository;

    private final ForumUserQueryService forumUserQueryService;

    private final PostCommandService postCommandService;

    private final ModelMapper modelMapper;

    @Override
    public ForumThread create(CreateForumThreadRequest request) {
        ForumThread newThread = modelMapper.map(request, ForumThread.class);
        newThread.setAuthor(forumUserQueryService.getById(request.getAuthorId()));
        newThread.setCreationDateTime(LocalDateTime.now());

        forumThreadRepository.save(newThread);

        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setThreadId(newThread.getId());
        postRequest.setContent(request.getFirstPostContent());
        postRequest.setAuthorId(UserUtil.getCurrentUser().getId());

        postCommandService.create(postRequest);

        return newThread;
    }
}
