package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findBySectionId(Long sectionId);
}
