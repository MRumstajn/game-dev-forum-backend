package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferRequest;
import com.mrumstajn.gamedevforum.entity.WorkOffer;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.WorkOfferRepository;
import com.mrumstajn.gamedevforum.service.command.WorkOfferCommandService;
import com.mrumstajn.gamedevforum.service.query.WorkOfferQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkOfferCommandServiceImpl implements WorkOfferCommandService {
    private final WorkOfferRepository workOfferRepository;

    private final WorkOfferQueryService workOfferQueryService;

    private final ModelMapper modelMapper;

    @Override
    public WorkOffer create(CreateWorkOfferRequest request) {
        WorkOffer workOffer = modelMapper.map(request, WorkOffer.class);
        workOffer.setAuthor(UserUtil.getCurrentUser());

        return workOfferRepository.save(workOffer);
    }

    @Override
    public WorkOffer edit(Long id, EditWorkOfferRequest request) {
        WorkOffer existingWorkOffer = workOfferQueryService.getById(id);

        if (!isUserOwnerOfWorkOffer(existingWorkOffer) && !UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("User is not the owner of the specified work offer");
        }

        modelMapper.map(request, existingWorkOffer);

        return workOfferRepository.save(existingWorkOffer);
    }

    @Override
    public void delete(Long id) {
        WorkOffer existingWorkOffer = workOfferQueryService.getById(id);

        if (!isUserOwnerOfWorkOffer(existingWorkOffer) && !UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("User is not the owner of the specified work offer");
        }

        workOfferRepository.delete(existingWorkOffer);
    }

    private boolean isUserOwnerOfWorkOffer(WorkOffer workOffer){
        return Objects.equals(UserUtil.getCurrentUser().getId(), workOffer.getAuthor().getId());
    }
}
