package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchCategoriesRequestPageable;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.repository.CategoryRepository;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Page<Category> search(SearchCategoriesRequestPageable request) {
        SearchConfiguration<Category, Category, SearchCategoriesRequestPageable> searchConfiguration = SearchConfiguration.<Category, Category, SearchCategoriesRequestPageable>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(Category.class)
                .build();

        return categoryRepository.findAll(request, searchConfiguration, PageableUtil.convertToPageable(request));
    }

    @Override
    public List<Category> getTopNSortedByThreadCount(int count) {
        return categoryRepository.findTopByThreadCount(Pageable.ofSize(count).withPage(0));
    }

    @Override
    public long getTotalCount() {
        return categoryRepository.count();
    }
}
