package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.entity.WorkOfferRating;
import com.mrumstajn.gamedevforum.repository.WorkOfferRatingRepository;
import com.mrumstajn.gamedevforum.service.query.WorkOfferRatingQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}