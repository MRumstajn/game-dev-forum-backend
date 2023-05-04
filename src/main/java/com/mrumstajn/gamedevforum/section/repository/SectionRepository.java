package com.mrumstajn.gamedevforum.section.repository;

import com.mrumstajn.gamedevforum.section.entity.Section;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long>, SearchExecutor<Section> {
}
