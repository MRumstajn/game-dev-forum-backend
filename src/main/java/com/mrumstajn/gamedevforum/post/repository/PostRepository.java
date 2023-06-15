package com.mrumstajn.gamedevforum.post.repository;

import com.mrumstajn.gamedevforum.post.entity.Post;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, SearchExecutor<Post> {

    @Query("""
            SELECT p FROM Post p WHERE p.creationDateTime >= (select MAX(p2.creationDateTime)
            FROM Post p2 WHERE p2.thread.id = :threadId) AND p.thread.id= :threadId
                """)
    Post findLatestByCreationDateAndThreadId(@Param("threadId") Long threadId);

    Long countAllByThreadId(Long threadId);

    Post findFirstByThreadId(Long threadId);

    @Query("""
            SELECT p FROM Post p WHERE p.thread.id = :threadId AND (SELECT COUNT(pr)
            FROM UserPostReaction pr WHERE pr.postId = p.id AND pr.postReactionType = 'LIKE') >= 1
            ORDER BY (SELECT COUNT(pr) FROM UserPostReaction pr WHERE pr.postId = p.id AND pr.postReactionType = 'LIKE') DESC, p.id ASC
            """)
    List<Post> findAllTopByLikeCountInThread(@Param("threadId") Long threadId);
}
