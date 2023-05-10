package com.mrumstajn.gamedevforum.user.service.query.impl;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.repository.ForumUserRepository;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumUserQueryServiceImpl implements ForumUserQueryService {
    private final ForumUserRepository forumUserRepository;

    @Override
    public ForumUser getById(Long id) {
        return forumUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<ForumUser> getAllById(List<Long> ids) {
        return forumUserRepository.findAllById(ids);
    }

    @Override
    public ForumUser getByUsernameExact(String username) {
        return forumUserRepository.findDistinctByUsername(username);
    }

    @Override
    public Long getTotalCount() {
        return forumUserRepository.count();
    }

    @Override
    public Boolean isUsernameTaken(String username) {
        return forumUserRepository.existsByUsername(username);
    }

    @Override
    public List<ForumUser> getTopByReputation() {
        return forumUserRepository.findTopByReputation(Pageable.ofSize(3).withPage(0));
    }
}
