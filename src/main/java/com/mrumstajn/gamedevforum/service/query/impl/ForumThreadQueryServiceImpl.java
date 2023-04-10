package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchForumThreadRequestPageable;
import com.mrumstajn.gamedevforum.dto.request.SearchLatestForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchLatestPostRequest;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.repository.ForumThreadRepository;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumThreadQueryServiceImpl implements ForumThreadQueryService {
    private final ForumThreadRepository forumThreadRepository;

    private final PostQueryService postQueryService;

    private final CategoryQueryService categoryQueryService;

    @Override
    public ForumThread getById(Long id) {
        return forumThreadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Thread with id " + id + " not found"));
    }

    @Override
    public Page<ForumThread> search(SearchForumThreadRequestPageable request) {
        SearchConfiguration<ForumThread, ForumThread, SearchForumThreadRequestPageable> searchConfiguration = SearchConfiguration.<ForumThread, ForumThread, SearchForumThreadRequestPageable>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(ForumThread.class)
                .build();

        return forumThreadRepository.findAll(request, searchConfiguration, PageableUtil.convertToPageable(request));
    }

    @Override
    public Long getTotalCount() {
        return forumThreadRepository.count();
    }

    @Override
    public Long getTotalCountByCategoryId(Long categoryId) {
        categoryQueryService.getById(categoryId); // check if category exists first

        return forumThreadRepository.countAllByCategoryId(categoryId);
    }

    @Override
    public ForumThread getLatest(SearchLatestForumThreadRequest request) {
        long threadCount = forumThreadRepository.countAllByCategoryId(request.getCategoryId());

        if (threadCount == 0){
            return null;
        }
        if (threadCount < 2){
            List<ForumThread> threads = forumThreadRepository.findAll();

            ForumThread latestThread = threads.get(0);

            Post latestPost = postQueryService.getLatest(new SearchLatestPostRequest(latestThread.getId()));
            if (latestPost != null){
                return latestThread;
            }
            return null;
        }

        return forumThreadRepository.findByCategoryIdAndLatestActivity(request.getCategoryId());
    }
}
