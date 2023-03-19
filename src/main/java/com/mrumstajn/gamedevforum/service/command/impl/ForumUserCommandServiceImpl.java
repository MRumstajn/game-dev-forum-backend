package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.repository.ForumUserRepository;
import com.mrumstajn.gamedevforum.service.command.ForumUserCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumUserCommandServiceImpl implements ForumUserCommandService {
    private final ForumUserRepository forumUserRepository;

    private final ModelMapper modelMapper;

    @Override
    public ForumUser create(CreateForumUserRequest request) {
        ForumUser newUser = modelMapper.map(request, ForumUser.class);
        newUser.setJoinDate(LocalDate.now());

        return forumUserRepository.save(newUser);
    }
}
