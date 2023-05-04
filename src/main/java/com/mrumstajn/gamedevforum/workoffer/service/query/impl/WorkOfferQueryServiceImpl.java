package com.mrumstajn.gamedevforum.workoffer.service.query.impl;

import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferRequestPageable;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOffer;
import com.mrumstajn.gamedevforum.workoffer.repository.WorkOfferRepository;
import com.mrumstajn.gamedevforum.workoffer.service.query.WorkOfferQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import net.croz.nrich.search.api.util.PageableUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkOfferQueryServiceImpl implements WorkOfferQueryService {
    private final WorkOfferRepository workOfferRepository;

    @Override
    public WorkOffer getById(Long id) {
        return workOfferRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Work offer with id " + id + " does not exist"));
    }

    @Override
    public Page<WorkOffer> searchPageable(SearchWorkOfferRequestPageable requestPageable) {
        SearchConfiguration<WorkOffer, WorkOffer, SearchWorkOfferRequestPageable> searchConfiguration = SearchConfiguration.<WorkOffer, WorkOffer, SearchWorkOfferRequestPageable>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(WorkOffer.class)
                .build();

        return workOfferRepository.findAll(requestPageable, searchConfiguration, PageableUtil.convertToPageable(requestPageable));
    }


}
