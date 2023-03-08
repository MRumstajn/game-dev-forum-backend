package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByThreadId(Long threadID);
}
