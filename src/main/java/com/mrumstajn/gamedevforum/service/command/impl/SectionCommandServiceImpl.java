package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.entity.Section;
import com.mrumstajn.gamedevforum.repository.SectionRepository;
import com.mrumstajn.gamedevforum.service.command.SectionCommandService;
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
        return sectionRepository.save(modelMapper.map(request, Section.class));
    }
}
