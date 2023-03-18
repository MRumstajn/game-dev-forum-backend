package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Post;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, SearchExecutor<Post> {

    List<Post> findByThreadId(Long threadID);
}
