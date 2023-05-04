package com.mrumstajn.gamedevforum.workoffer.service.command;

import com.mrumstajn.gamedevforum.workoffer.dto.request.CreateWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.EditWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferRating;

public interface WorkOfferRatingCommandService {

    WorkOfferRating create(CreateWorkOfferRatingRequest request);

    WorkOfferRating edit(Long id, EditWorkOfferRatingRequest request);

    void deleteForWorkOfferAndCurrentUser(Long workOfferId);

    void deleteAllForWorkOffer(Long workOfferId);
}
