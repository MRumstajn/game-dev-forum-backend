package com.mrumstajn.gamedevforum.post.service.query.impl;

import com.mrumstajn.gamedevforum.common.constants.PaginationConstants;
import com.mrumstajn.gamedevforum.post.dto.request.SearchThreadForPostRequest;
import com.mrumstajn.gamedevforum.post.dto.request.SearchPostRequestPageable;
import com.mrumstajn.gamedevforum.post.dto.request.SearchUserPostReactionCountRequest;
import com.mrumstajn.gamedevforum.post.dto.response.PostReactionTypeCountResponse;
import com.mrumstajn.gamedevforum.post.entity.Post;
import com.mrumstajn.gamedevforum.post.entity.PostReactionType;
import com.mrumstajn.gamedevforum.post.entity.UserPostReaction;
import com.mrumstajn.gamedevforum.post.repository.PostRepository;
import com.mrumstajn.gamedevforum.post.service.query.PostQueryService;
import com.mrumstajn.gamedevforum.post.service.query.UserPostReactionQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
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

    private final EntityManager entityManager;

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));
    }

    @Override
    public Page<Post> search(SearchPostRequestPageable request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);

        List<Predicate> predicates = new ArrayList<>();

        // thread filter
        if (request.getThreadId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("thread").get("id"), request.getThreadId()));
        }

        // user id filter
        if (request.getAuthorId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("author").get("id"), request.getAuthorId()));
        }

        // username filter
        if (request.getAuthorUsername() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("username")), "%" + request.getAuthorUsername().toLowerCase() + "%"));
        }

        // creation date time filter
        if (request.getCreationDateTimeFromIncluding() != null && request.getCreationDateTimeToIncluding() == null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDateTime"), request.getCreationDateTimeFromIncluding()));
        } else if (request.getCreationDateTimeToIncluding() != null && request.getCreationDateTimeFromIncluding() == null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDateTime"), request.getCreationDateTimeToIncluding()));
        } else if (request.getCreationDateTimeFromIncluding() != null && request.getCreationDateTimeToIncluding() != null) {
            predicates.add(criteriaBuilder.between(root.get("creationDateTime"), request.getCreationDateTimeFromIncluding(), request.getCreationDateTimeToIncluding()));
        }

        // like filter
        if (request.getLikesFromIncluding() != null) {
            Subquery<Long> likeCountSubquery = query.subquery(Long.class);
            Root<UserPostReaction> reactionRoot = likeCountSubquery.from(UserPostReaction.class);
            likeCountSubquery.select(criteriaBuilder.count(reactionRoot));
            likeCountSubquery.where(
              criteriaBuilder.equal(reactionRoot.get("postId"), root.get("id")),
              criteriaBuilder.equal(reactionRoot.get("postReactionType"), PostReactionType.LIKE)
            );

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(likeCountSubquery, request.getLikesFromIncluding()));
        }

        // dislike filter
        if (request.getDislikesFromIncluding() != null) {
            Subquery<Long> dislikeCountSubquery = query.subquery(Long.class);
            Root<UserPostReaction> reactionRoot = dislikeCountSubquery.from(UserPostReaction.class);
            dislikeCountSubquery.select(criteriaBuilder.count(reactionRoot));
            dislikeCountSubquery.where(
              criteriaBuilder.equal(reactionRoot.get("postId"), root.get("id")),
              criteriaBuilder.equal(reactionRoot.get("postReactionType"), PostReactionType.DISLIKE)
            );

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(dislikeCountSubquery, request.getDislikesFromIncluding()));
        }

        if (!predicates.isEmpty()) {
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        query.orderBy(criteriaBuilder.asc(root.get("creationDateTime")));

        TypedQuery<Post> mainQuery = entityManager.createQuery(query);
        mainQuery.setMaxResults(request.getPageSize());
        mainQuery.setFirstResult(request.getPageNumber() * request.getPageSize());

        // Return the paged result
        return new PageImpl<>(mainQuery.getResultList(), PageableUtil.convertToPageable(request), countAllBasedOnSearchRequest(request));
    }

    private Long countAllBasedOnSearchRequest(SearchPostRequestPageable request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Post> root = query.from(Post.class);

        List<Predicate> predicates = new ArrayList<>();

        // thread filter
        if (request.getThreadId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("thread").get("id"), request.getThreadId()));
        }

        // user id filter
        if (request.getAuthorId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("author").get("id"), request.getAuthorId()));
        }

        // username filter
        if (request.getAuthorUsername() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author").get("username")), "%" + request.getAuthorUsername().toLowerCase() + "%"));
        }

        // creation date time filter
        if (request.getCreationDateTimeFromIncluding() != null && request.getCreationDateTimeToIncluding() == null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDateTime"), request.getCreationDateTimeFromIncluding()));
        } else if (request.getCreationDateTimeToIncluding() != null && request.getCreationDateTimeFromIncluding() == null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDateTime"), request.getCreationDateTimeToIncluding()));
        } else if (request.getCreationDateTimeFromIncluding() != null && request.getCreationDateTimeToIncluding() != null) {
            predicates.add(criteriaBuilder.between(root.get("creationDateTime"), request.getCreationDateTimeFromIncluding(), request.getCreationDateTimeToIncluding()));
        }

        // like filter
        if (request.getLikesFromIncluding() != null) {
            Subquery<Long> likeCountSubquery = query.subquery(Long.class);
            Root<UserPostReaction> reactionRoot = likeCountSubquery.from(UserPostReaction.class);
            likeCountSubquery.select(criteriaBuilder.count(reactionRoot));
            likeCountSubquery.where(
              criteriaBuilder.equal(reactionRoot.get("postId"), root.get("id")),
              criteriaBuilder.equal(reactionRoot.get("postReactionType"), PostReactionType.LIKE)
            );

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(likeCountSubquery, request.getLikesFromIncluding()));
        }

        // dislike filter
        if (request.getDislikesFromIncluding() != null) {
            Subquery<Long> dislikeCountSubquery = query.subquery(Long.class);
            Root<UserPostReaction> reactionRoot = dislikeCountSubquery.from(UserPostReaction.class);
            dislikeCountSubquery.select(criteriaBuilder.count(reactionRoot));
            dislikeCountSubquery.where(
              criteriaBuilder.equal(reactionRoot.get("postId"), root.get("id")),
              criteriaBuilder.equal(reactionRoot.get("postReactionType"), PostReactionType.DISLIKE)
            );

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(dislikeCountSubquery, request.getDislikesFromIncluding()));
        }

        if (!predicates.isEmpty()) {
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }

        query.select(criteriaBuilder.count(root));

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
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
