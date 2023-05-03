package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferCategory;

public interface WorkOfferCategoryCommandService {

    WorkOfferCategory create(CreateWorkOfferCategoryRequest request);

    WorkOfferCategory edit(Long id, EditWorkOfferCategoryRequest request);

    void delete(Long id);
}
