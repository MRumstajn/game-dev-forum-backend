package com.mrumstajn.gamedevforum.user.repository;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumUserRepository extends JpaRepository<ForumUser, Long>, SearchExecutor<ForumUser> {

    ForumUser findDistinctByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("""
            SELECT u FROM ForumUser u WHERE u.reputation > 0 ORDER BY u.reputation DESC
            """)
    List<ForumUser> findTopByReputation(Pageable pageable);
}
