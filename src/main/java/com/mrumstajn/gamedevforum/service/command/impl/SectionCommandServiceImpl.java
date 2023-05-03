package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.entity.Section;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.SectionRepository;
import com.mrumstajn.gamedevforum.service.command.SectionCommandService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionCommandServiceImpl implements SectionCommandService {

    private final SectionRepository sectionRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Section create(CreateSectionRequest request) {
        if (!UserUtil.isUserAdmin()){
            throw new UnauthorizedActionException("Only ADMIN users can create categories");
        }

        return sectionRepository.save(modelMapper.map(request, Section.class));
    }
}
