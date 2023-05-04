package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.dto.request.EditCategoryRequest;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.repository.CategoryRepository;
import com.mrumstajn.gamedevforum.service.command.CategoryCommandService;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import com.mrumstajn.gamedevforum.service.query.SectionQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;

    private final CategoryQueryService categoryQueryService;

    private final SectionQueryService sectionQueryService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Category create(CreateCategoryRequest request) {
        Category newCategory = modelMapper.map(request, Category.class);
        newCategory.setSection(sectionQueryService.getById(request.getSectionIdentifier()));

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category edit(Long id, EditCategoryRequest request) {
        Category existingCategory = categoryQueryService.getById(id);
        existingCategory.setTitle(request.getTitle());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(categoryQueryService.getById(id));
    }
}
