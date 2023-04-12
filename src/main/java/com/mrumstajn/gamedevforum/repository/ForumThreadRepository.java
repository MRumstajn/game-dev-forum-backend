package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.ForumThread;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ForumThreadRepository extends JpaRepository<ForumThread, Long>, SearchExecutor<ForumThread> {
    Long countAllByCategoryId(Long categoryId);


    @Query("""
            SELECT t FROM ForumThread t WHERE t.category.id = :categoryId AND t.id IN (SELECT p.thread.id FROM Post p WHERE p.creationDateTime >=
            (SELECT MAX(p2.creationDateTime) FROM Post p2 WHERE NOT p2.id = p.id AND p2.thread.id IN
            (SELECT t2.id FROM ForumThread t2 WHERE t2.category.id = :categoryId GROUP BY t2.id)) GROUP BY p.thread.id)
            """)
    ForumThread findByCategoryIdAndLatestActivity(@Param("categoryId") Long categoryId);

    ForumThread findFirstByCategoryId(Long categoryId);
}
