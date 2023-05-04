package com.mrumstajn.gamedevforum.workoffer.service.query;

import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferCategoryRequestPageable;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferCategory;
import org.springframework.data.domain.Page;

public interface WorkOfferCategoryQueryService {

    Page<WorkOfferCategory> search(SearchWorkOfferCategoryRequestPageable request);

    WorkOfferCategory getById(Long id);
}
