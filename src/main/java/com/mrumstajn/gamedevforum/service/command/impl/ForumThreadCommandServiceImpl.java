package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.repository.ForumThreadRepository;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumThreadCommandServiceImpl implements ForumThreadCommandService {
    private final ForumThreadRepository forumThreadRepository;

    private final ForumUserQueryService forumUserQueryService;

    private final ModelMapper modelMapper;

    @Override
    public ForumThread create(CreateForumThreadRequest request) {
        ForumThread newThread = modelMapper.map(request, ForumThread.class);
        newThread.setAuthor(forumUserQueryService.getById(request.getAuthorId()));
        newThread.setCreationDate(LocalDate.now());

        return forumThreadRepository.save(newThread);
    }
}
