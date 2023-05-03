package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.entity.WorkOfferRating;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.WorkOfferRatingRepository;
import com.mrumstajn.gamedevforum.service.command.WorkOfferRatingCommandService;
import com.mrumstajn.gamedevforum.service.query.WorkOfferQueryService;
import com.mrumstajn.gamedevforum.service.query.WorkOfferRatingQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import io.jsonwebtoken.lang.Objects;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkOfferRatingCommandServiceImpl implements WorkOfferRatingCommandService {
    private final WorkOfferQueryService workOfferQueryService;

    private final WorkOfferRatingRepository workOfferRatingRepository;

    private final WorkOfferRatingQueryService workOfferRatingQueryService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public WorkOfferRating create(CreateWorkOfferRatingRequest request) {
        workOfferQueryService.getById(request.getWorkOfferId());

        WorkOfferRating existingRating;
        try {
            existingRating = workOfferRatingQueryService.getByUserIdAndWorkOfferId(UserUtil.getCurrentUser().getId(), request.getWorkOfferId());
        } catch (EntityNotFoundException e){
            existingRating = null;
        }

        if (existingRating == null){
            existingRating = modelMapper.map(request, WorkOfferRating.class);
            existingRating.setUserId(UserUtil.getCurrentUser().getId());
        } else {
            existingRating.setRating(request.getRating());
        }

        return workOfferRatingRepository.save(existingRating);
    }

    @Override
    @Transactional
    public WorkOfferRating edit(Long id, EditWorkOfferRatingRequest request) {
        WorkOfferRating existingRating = workOfferRatingQueryService.getById(id);

        if (!doesUserOwnRating(existingRating) && !UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("User is not the owner of the specified work offer rating");
        }

        existingRating.setRating(request.getRating());

        return workOfferRatingRepository.save(existingRating);
    }

    @Override
    @Transactional
    public void deleteForWorkOfferAndCurrentUser(Long workOfferId) {
        WorkOfferRating existingRating = workOfferRatingQueryService.getByUserIdAndWorkOfferId(UserUtil.getCurrentUser().getId(), workOfferId);

        if (existingRating == null){
            throw new EntityNotFoundException("User has not left a rating for the specified work offer");
        }

        if (!doesUserOwnRating(existingRating) && !UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("User is not the owner of the specified work offer rating");
        }

        workOfferRatingRepository.delete(existingRating);
    }

    @Override
    @Transactional
    public void deleteAllForWorkOffer(Long workOfferId) {
        workOfferRatingRepository.deleteAllByWorkOfferId(workOfferId);
    }

    private boolean doesUserOwnRating(WorkOfferRating rating){
        return Objects.nullSafeEquals(UserUtil.getCurrentUser().getId(), rating.getUserId());
    }
}
