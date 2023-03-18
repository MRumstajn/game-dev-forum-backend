package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.ForumThread;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumThreadRepository extends JpaRepository<ForumThread, Long>, SearchExecutor<ForumThread> {

    List<ForumThread> findByCategoryId(Long categoryId);

    Long countAllByCategoryId(Long categoryId);
}
