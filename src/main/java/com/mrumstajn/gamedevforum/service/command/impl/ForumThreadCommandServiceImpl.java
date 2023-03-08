package com.mrumstajn.gamedevforum.service.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.repository.ForumThreadRepository;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ForumThreadCommandServiceImpl implements ForumThreadCommandService {
    private final ForumThreadRepository forumThreadRepository;

    private final ForumUserQueryService forumUserQueryService;

    private final ObjectMapper mapper;

    @Override
    public ForumThread create(CreateForumThreadRequest request) {
        ForumThread newThread = mapper.convertValue(request, ForumThread.class);
        newThread.setAuthor(forumUserQueryService.getById(request.getAuthorId()));
        newThread.setCreationDate(LocalDate.now());

        return forumThreadRepository.save(newThread);
    }
}
