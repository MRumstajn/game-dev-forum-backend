package com.mrumstajn.gamedevforum.workoffer.service.command;

import com.mrumstajn.gamedevforum.workoffer.dto.request.CreateWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.EditWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferCategory;

public interface WorkOfferCategoryCommandService {

    WorkOfferCategory create(CreateWorkOfferCategoryRequest request);

    WorkOfferCategory edit(Long id, EditWorkOfferCategoryRequest request);

    void delete(Long id);
}
