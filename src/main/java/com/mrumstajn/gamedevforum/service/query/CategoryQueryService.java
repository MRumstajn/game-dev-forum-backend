package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchCategoriesRequestPageable;
import com.mrumstajn.gamedevforum.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryQueryService {

    Category getById(Long id);

    Page<Category> search(SearchCategoriesRequestPageable request);

    List<Category> getTopNSortedByThreadCount(int count);

    long getTotalCount();
}
