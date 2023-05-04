package com.mrumstajn.gamedevforum.user.repository;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumUserRepository extends JpaRepository<ForumUser, Long>, SearchExecutor<ForumUser> {

    ForumUser findDistinctByUsername(String username);

    Boolean existsByUsername(String username);
}
