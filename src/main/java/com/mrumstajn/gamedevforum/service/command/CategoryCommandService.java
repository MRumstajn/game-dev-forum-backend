package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.entity.Category;

public interface CategoryCommandService {

    Category create(CreateCategoryRequest request);
}
