package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Section;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long>, SearchExecutor<Section> {
    List<Section> findByTitle(String title);
}
