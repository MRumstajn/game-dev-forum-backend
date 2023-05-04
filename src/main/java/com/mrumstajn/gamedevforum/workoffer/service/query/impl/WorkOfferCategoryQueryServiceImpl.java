package com.mrumstajn.gamedevforum.workoffer.service.query.impl;

import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferCategoryRequestPageable;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferCategory;
import com.mrumstajn.gamedevforum.workoffer.repository.WorkOfferCategoryRepository;
import com.mrumstajn.gamedevforum.workoffer.service.query.WorkOfferCategoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkOfferCategoryQueryServiceImpl implements WorkOfferCategoryQueryService {
    private final WorkOfferCategoryRepository workOfferCategoryRepository;

    @Override
    public Page<WorkOfferCategory> search(SearchWorkOfferCategoryRequestPageable request) {
        SearchConfiguration<WorkOfferCategory, WorkOfferCategory, SearchWorkOfferCategoryRequestPageable> searchConfiguration = SearchConfiguration.<WorkOfferCategory, WorkOfferCategory, SearchWorkOfferCategoryRequestPageable>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(WorkOfferCategory.class)
                .build();

        return workOfferCategoryRepository.findAll(request, searchConfiguration, PageableUtil.convertToPageable(request));
    }

    @Override
    public WorkOfferCategory getById(Long id) {
        return workOfferCategoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Work offer category with id " + id + " does not exist"));
    }
}
