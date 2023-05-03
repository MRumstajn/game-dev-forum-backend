package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchWorkOfferCategoryRequestPageable;
import com.mrumstajn.gamedevforum.entity.WorkOfferCategory;
import org.springframework.data.domain.Page;

public interface WorkOfferCategoryQueryService {

    Page<WorkOfferCategory> search(SearchWorkOfferCategoryRequestPageable request);

    WorkOfferCategory getById(Long id);
}
