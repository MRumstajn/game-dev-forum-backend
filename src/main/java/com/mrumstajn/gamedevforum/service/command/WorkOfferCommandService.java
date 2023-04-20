package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferRequest;
import com.mrumstajn.gamedevforum.entity.WorkOffer;

public interface WorkOfferCommandService {

    WorkOffer create(CreateWorkOfferRequest request);

    WorkOffer edit(Long id, EditWorkOfferRequest request);

    void delete(Long id);
}
