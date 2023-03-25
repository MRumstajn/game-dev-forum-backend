package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.repository.ForumUserRepository;
import com.mrumstajn.gamedevforum.service.command.ForumUserCommandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumUserCommandServiceImpl implements ForumUserCommandService {
    private final ForumUserRepository forumUserRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ForumUser create(CreateForumUserRequest request) {
        ForumUser newUser = modelMapper.map(request, ForumUser.class);
        newUser.setJoinDate(LocalDate.now());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        return forumUserRepository.save(newUser);
    }
}
