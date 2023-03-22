package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.ForumThread;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForumThreadRepository extends JpaRepository<ForumThread, Long>, SearchExecutor<ForumThread> {

    List<ForumThread> findByCategoryId(Long categoryId);

    Long countAllByCategoryId(Long categoryId);

    @Query("""
            SELECT t FROM ForumThread t WHERE t.id = (SELECT p.threadId FROM Post p WHERE p.creationDateTime >= (select MAX(p2.creationDateTime) FROM Post p2 WHERE p2.id != p.id) AND t.categoryId = :categoryId GROUP BY p.threadId)
            """)
    ForumThread findByCategoryIdAndLatestActivity(@Param("categoryId") Long categoryId);
}
