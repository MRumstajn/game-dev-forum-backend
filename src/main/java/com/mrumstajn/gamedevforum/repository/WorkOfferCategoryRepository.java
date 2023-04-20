package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.WorkOfferCategory;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOfferCategoryRepository extends JpaRepository<WorkOfferCategory, Long>, SearchExecutor<WorkOfferCategory> {
}
