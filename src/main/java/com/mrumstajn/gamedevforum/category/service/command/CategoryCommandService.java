package com.mrumstajn.gamedevforum.category.service.command;

import com.mrumstajn.gamedevforum.category.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.category.dto.request.EditCategoryRequest;
import com.mrumstajn.gamedevforum.category.entity.Category;

public interface CategoryCommandService {

    Category create(CreateCategoryRequest request);

    Category edit(Long id, EditCategoryRequest request);

    void delete(Long id);
}
