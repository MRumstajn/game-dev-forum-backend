package com.mrumstajn.gamedevforum.category.service.query;

import com.mrumstajn.gamedevforum.category.dto.request.SearchCategoriesRequestPageable;
import com.mrumstajn.gamedevforum.category.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryQueryService {

    Category getById(Long id);

    Page<Category> search(SearchCategoriesRequestPageable request);

    List<Category> getTopNSortedByThreadCount(int count);

    long getTotalCount();
}
