package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferRating;

import java.util.List;

public interface WorkOfferRatingQueryService {

    Integer getAverageRating(Long workOfferId);

    Long getTotalRatings(Long workOfferId);

    WorkOfferRating getByUserIdAndWorkOfferId(Long userId, Long workOfferId);

    WorkOfferRating getById(Long id);

    List<WorkOfferRating> search(SearchWorkOfferRatingRequest request);
}
