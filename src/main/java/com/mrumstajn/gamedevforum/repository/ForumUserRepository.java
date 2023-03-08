package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumUserRepository extends JpaRepository<ForumUser, Long> {
}
