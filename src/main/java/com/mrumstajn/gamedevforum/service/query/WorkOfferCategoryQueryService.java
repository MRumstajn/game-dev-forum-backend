package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferCategory;

import java.util.List;

public interface WorkOfferCategoryQueryService {

    List<WorkOfferCategory> search(SearchWorkOfferCategoryRequest request);

    WorkOfferCategory getById(Long id);
}
