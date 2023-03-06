package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
