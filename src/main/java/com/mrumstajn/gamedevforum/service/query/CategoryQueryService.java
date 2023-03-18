package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchCategoriesRequest;
import com.mrumstajn.gamedevforum.entity.Category;

import java.util.List;

public interface CategoryQueryService {

    Category getById(Long id);

    List<Category> search(SearchCategoriesRequest request);

    List<Category> getTopNSortedByThreadCount(int count);

    long getTotalCount();
}
