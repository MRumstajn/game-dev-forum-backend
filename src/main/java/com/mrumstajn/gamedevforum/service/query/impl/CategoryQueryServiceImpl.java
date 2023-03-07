package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchCategoriesRequest;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.repository.CategoryRepository;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public List<Category> search(SearchCategoriesRequest request) {
        return categoryRepository.findBySectionId(request.getSectionId());
    }
}
