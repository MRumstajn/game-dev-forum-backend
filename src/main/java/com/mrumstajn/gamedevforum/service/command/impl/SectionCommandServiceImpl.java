package com.mrumstajn.gamedevforum.service.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.entity.Section;
import com.mrumstajn.gamedevforum.repository.SectionRepository;
import com.mrumstajn.gamedevforum.service.command.SectionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionCommandServiceImpl implements SectionCommandService {

    private final SectionRepository sectionRepository;

    private final ObjectMapper mapper;

    @Override
    public Section create(CreateSectionRequest request) {
        return sectionRepository.save(mapper.convertValue(request, Section.class));
    }
}
