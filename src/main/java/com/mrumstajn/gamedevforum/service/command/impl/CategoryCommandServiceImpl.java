package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.CategoryRepository;
import com.mrumstajn.gamedevforum.service.command.CategoryCommandService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Category create(CreateCategoryRequest request) {
        if (UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN){
            throw new UnauthorizedActionException("Only ADMIN users can create categories");
        }

        Category newCategory = modelMapper.map(request, Category.class);
        newCategory.setSectionId(request.getSectionIdentifier());

        return categoryRepository.save(newCategory);
    }
}
