package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.repository.CategoryRepository;
import com.mrumstajn.gamedevforum.service.command.CategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public Category create(CreateCategoryRequest request) {
        return categoryRepository.save(modelMapper.map(request, Category.class));
    }
}
