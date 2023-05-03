package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchWorkOfferRequestPageable;
import com.mrumstajn.gamedevforum.entity.WorkOffer;
import org.springframework.data.domain.Page;

public interface WorkOfferQueryService {

    WorkOffer getById(Long id);

    Page<WorkOffer> searchPageable(SearchWorkOfferRequestPageable requestPageable);
}
