package com.mrumstajn.gamedevforum.workoffer.service.command;

import com.mrumstajn.gamedevforum.workoffer.dto.request.CreateWorkOfferRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.EditWorkOfferRequest;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOffer;

public interface WorkOfferCommandService {

    WorkOffer create(CreateWorkOfferRequest request);

    WorkOffer edit(Long id, EditWorkOfferRequest request);

    void delete(Long id);
}
