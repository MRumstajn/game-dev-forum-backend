package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.dto.request.SearchWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferCategory;
import com.mrumstajn.gamedevforum.repository.WorkOfferCategoryRepository;
import com.mrumstajn.gamedevforum.service.query.WorkOfferCategoryQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOfferCategoryQueryServiceImpl implements WorkOfferCategoryQueryService {
    private final WorkOfferCategoryRepository workOfferCategoryRepository;

    @Override
    public List<WorkOfferCategory> search(SearchWorkOfferCategoryRequest request) {
        SearchConfiguration<WorkOfferCategory, WorkOfferCategory, SearchWorkOfferCategoryRequest> searchConfiguration = SearchConfiguration.<WorkOfferCategory, WorkOfferCategory, SearchWorkOfferCategoryRequest>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(WorkOfferCategory.class)
                .build();

        return workOfferCategoryRepository.findAll(request, searchConfiguration);
    }

    @Override
    public WorkOfferCategory getById(Long id) {
        return workOfferCategoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Work offer category with id " + id + " does not exist"));
    }
}
