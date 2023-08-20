package com.mrumstajn.gamedevforum.post.service.query.impl;

import com.mrumstajn.gamedevforum.common.constants.PaginationConstants;
import com.mrumstajn.gamedevforum.post.dto.request.SearchThreadForPostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchPostRequestPageable;
import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.post.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.post.entity.Post;
import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import com.mrumstajn.gamedevforum.post.repository.PostRepository;
import com.mrumstajn.gamedevforum.post.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.post.service.query.UserPostReactionQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {
    private final PostRepository postRepository;

    private final UserPostReactionQueryService userPostReactionQueryService;

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));
    }

    @Override
    public Page<Post> search(SearchPostRequestPageable request) {
        SearchConfiguration<Post, Post, SearchPostRequestPageable> searchConfiguration = SearchConfiguration.<Post, Post, SearchPostRequestPageable>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(Post.class)
                .build();

        Page<Post> matches = postRepository.findAll(request, searchConfiguration, PageableUtil.convertToPageable(request));

        SearchUserPostReactionCountRequest reactionCountRequest = new SearchUserPostReactionCountRequest();
        reactionCountRequest.setPostIds(matches.map(Post::getId).stream().toList());
        List<PostReactionTypeCountResponse> typeCountResponses = userPostReactionQueryService.getReactionCountForAll(reactionCountRequest);

        List<Long> matchedPostsToRemove = new ArrayList<>();

        // filter by likes
        if (request.getLikesFromIncluding() != null) {
            typeCountResponses.forEach(response -> {
                if (response.getPostReactionType() == PostReactionType.LIKE && response.getCount() < request.getLikesFromIncluding()) {
                    matchedPostsToRemove.add(response.getPostId());
                }
            });
        }

        // filter by dislikes
        if (request.getDislikesFromIncluding() != null) {
            typeCountResponses.forEach(response -> {
                if (response.getPostReactionType() == PostReactionType.DISLIKE && response.getCount() < request.getDislikesFromIncluding()) {
                    matchedPostsToRemove.add(response.getPostId());
                }
            });
        }
        
        List<Post> filteredPosts = matches.filter(match -> !matchedPostsToRemove.contains(match.getId())).toList();

        return new PageImpl<>(filteredPosts, matches.getPageable(), matches.getTotalElements());
    }

    @Override
    public Long getTotalCount() {
        return postRepository.count();
    }

    @Override
    public Long getTotalCountByThreadId(Long threadId) {
        return postRepository.countAllByThreadId(threadId);
    }

    @Override
    public Post getLatest(SearchThreadForPostRequest request) {
        long postCount = postRepository.countAllByThreadId(request.getThreadId());

        if (postCount == 0) {
            return null;
        }
        if (postCount < 2) {
            return postRepository.findFirstByThreadId(request.getThreadId());
        }

        return postRepository.findLatestByCreationDateAndThreadId(request.getThreadId());
    }

    @Override
    public Post getTopByLikesInThread(Long threadId) {
        List<Post> topPostsByLikes = postRepository.findAllTopByLikeCountInThread(threadId);
        if (topPostsByLikes.size() == 0) {
            return null;
        }

        // detect if there are multiple posts with the same number of likes, and if so return null
        if (topPostsByLikes.size() > 1) {
            Post topPost = topPostsByLikes.get(0);
            Post topPostPrevious = topPostsByLikes.get(1);

            SearchUserPostReactionCountRequest searchUserPostReactionCountRequest = new SearchUserPostReactionCountRequest();
            searchUserPostReactionCountRequest.setPostIds(List.of(topPost.getId(), topPostPrevious.getId()));
            List<PostReactionTypeCountResponse> reactionCountResponse = userPostReactionQueryService.getReactionCountForAll(searchUserPostReactionCountRequest);

            long topPostLikeCount = reactionCountResponse.stream().filter(response -> Objects.equals(response.getPostId(), topPost.getId()) &&
                    response.getPostReactionType() == PostReactionType.LIKE).toList().get(0).getCount();
            long previousTopPostLikeCount = reactionCountResponse.stream().filter(response -> Objects.equals(response.getPostId(), topPostPrevious.getId()) &&
                    response.getPostReactionType() == PostReactionType.LIKE).toList().get(0).getCount();

            if (topPostLikeCount == previousTopPostLikeCount) {
                return null;
            }
        }

        return topPostsByLikes.get(0);
    }

    @Override
    public Long getTotalPageCountForThread(Long threadId) {
        int totalPages = Math.round((float) postRepository.countAllByThreadId(threadId) / PaginationConstants.THREAD_PAGE_SIZE);
        return (long) totalPages;
    }
}
