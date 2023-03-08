package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.repository.ForumUserRepository;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForumUserQueryServiceImpl implements ForumUserQueryService {
    private final ForumUserRepository forumUserRepository;

    @Override
    public ForumUser getById(Long id) {
        return forumUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }
}