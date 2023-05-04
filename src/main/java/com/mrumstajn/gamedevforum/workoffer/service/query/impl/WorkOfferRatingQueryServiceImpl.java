package com.mrumstajn.gamedevforum.workoffer.service.query.impl;

import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferRating;
import com.mrumstajn.gamedevforum.workoffer.repository.WorkOfferRatingRepository;
import com.mrumstajn.gamedevforum.workoffer.service.query.WorkOfferRatingQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOfferRatingQueryServiceImpl implements WorkOfferRatingQueryService {
    private final WorkOfferRatingRepository workOfferRatingRepository;

    @Override
    public Integer getAverageRating(Long workOfferId) {
        Double averageRating = workOfferRatingRepository.findAverageRatingByWorkOfferId(workOfferId);

        return averageRating != null ? averageRating.intValue() : 0;
    }

    @Override
    public Long getTotalRatings(Long workOfferId) {
        return workOfferRatingRepository.countAllByWorkOfferId(workOfferId);
    }

    @Override
    public WorkOfferRating getByUserIdAndWorkOfferId(Long userId, Long workOfferId) {
        return workOfferRatingRepository.findByUserIdAndWorkOfferId(userId, workOfferId).orElseThrow(() ->
                new EntityNotFoundException("User with id " + userId + " has not left a rating for work offer with id " + workOfferId));
    }

    @Override
    public WorkOfferRating getById(Long id) {
        return workOfferRatingRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Work offer rating with id " + id + " does not exist"));
    }

    @Override
    public List<WorkOfferRating> search(SearchWorkOfferRatingRequest request) {
        SearchConfiguration<WorkOfferRating, WorkOfferRating, SearchWorkOfferRatingRequest> searchConfiguration = SearchConfiguration.<WorkOfferRating, WorkOfferRating, SearchWorkOfferRatingRequest>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(WorkOfferRating.class)
                .build();

        return workOfferRatingRepository.findAll(request, searchConfiguration);
    }
}
