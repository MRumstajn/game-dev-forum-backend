package com.mrumstajn.gamedevforum.workoffer.service.query;

import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferRequestPageable;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOffer;
import org.springframework.data.domain.Page;

public interface WorkOfferQueryService {

    WorkOffer getById(Long id);

    Page<WorkOffer> searchPageable(SearchWorkOfferRequestPageable requestPageable);
}
