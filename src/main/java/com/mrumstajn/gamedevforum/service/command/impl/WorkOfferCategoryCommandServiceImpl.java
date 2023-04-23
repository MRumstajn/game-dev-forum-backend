package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferCategory;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.WorkOfferCategoryRepository;
import com.mrumstajn.gamedevforum.service.command.WorkOfferCategoryCommandService;
import com.mrumstajn.gamedevforum.service.query.WorkOfferCategoryQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkOfferCategoryCommandServiceImpl implements WorkOfferCategoryCommandService {
    private final WorkOfferCategoryRepository workOfferCategoryRepository;

    private final WorkOfferCategoryQueryService workOfferCategoryQueryService;

    private final ModelMapper modelMapper;

    @Override
    public WorkOfferCategory create(CreateWorkOfferCategoryRequest request) {
        if (!UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("Only ADMIN users can create work offer categories");
        }

        return workOfferCategoryRepository.save(modelMapper.map(request, WorkOfferCategory.class));
    }

    @Override
    public WorkOfferCategory edit(Long id, EditWorkOfferCategoryRequest request) {
        if (!UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("Only ADMIN users can create work offer categories");
        }

        WorkOfferCategory workOfferCategory = workOfferCategoryQueryService.getById(id);

        modelMapper.map(request, workOfferCategory);

        return workOfferCategoryRepository.save(workOfferCategory);
    }

    @Override
    public void delete(Long id) {
        if (!UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("Only ADMIN users can create work offer categories");
        }

        workOfferCategoryRepository.delete(workOfferCategoryQueryService.getById(id));
    }
}
