package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchForumThreadRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.repository.ForumThreadRepository;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumThreadQueryServiceImpl implements ForumThreadQueryService {
    private final ForumThreadRepository forumThreadRepository;

    @Override
    public ForumThread getById(Long id) {
        return forumThreadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Thread with id " + id + " not found"));
    }

    @Override
    public List<ForumThread> search(SearchForumThreadRequest request) {
        return forumThreadRepository.findByCategoryId(request.getCategoryId());
    }

    @Override
    public Long getTotalCount() {
        return forumThreadRepository.count();
    }

    @Override
    public Long getTotalCountByCategoryId(Long categoryId) {
        getById(categoryId); // check if category exists first

        return forumThreadRepository.countAllByCategoryId(categoryId);
    }
}
