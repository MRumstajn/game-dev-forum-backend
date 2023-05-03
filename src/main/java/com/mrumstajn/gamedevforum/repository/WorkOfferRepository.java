package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.WorkOffer;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOfferRepository extends JpaRepository<WorkOffer, Long>, SearchExecutor<WorkOffer> {
}
