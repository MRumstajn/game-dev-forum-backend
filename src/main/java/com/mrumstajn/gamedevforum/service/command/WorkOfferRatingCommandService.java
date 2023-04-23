package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferRating;

public interface WorkOfferRatingCommandService {

    WorkOfferRating create(CreateWorkOfferRatingRequest request);

    WorkOfferRating edit(Long id, EditWorkOfferRatingRequest request);

    void delete(Long id);

    void deleteAllForWorkOffer(Long workOfferId);
}
