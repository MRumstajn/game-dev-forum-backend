package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findBySectionId(Long sectionId);

    @Query("""
                SELECT c FROM Category c WHERE c.id IN (SELECT t.categoryId FROM ForumThread t GROUP BY t.categoryId ORDER BY COUNT(t) DESC)
            """)
    List<Category> findTopByThreadCount(Pageable pageable);
}
