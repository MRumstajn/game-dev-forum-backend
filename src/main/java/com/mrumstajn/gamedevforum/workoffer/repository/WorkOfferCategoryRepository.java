package com.mrumstajn.gamedevforum.workoffer.repository;

import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferCategory;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOfferCategoryRepository extends JpaRepository<WorkOfferCategory, Long>, SearchExecutor<WorkOfferCategory> {
}
