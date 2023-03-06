package com.mrumstajn.gamedevforum.service.query.impl;

import com.mrumstajn.gamedevforum.entity.Section;
import com.mrumstajn.gamedevforum.repository.SectionRepository;
import com.mrumstajn.gamedevforum.service.query.SectionQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionQueryServiceImpl implements SectionQueryService {

    private final SectionRepository sectionRepository;

    @Override
    public Section getById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Section with id " + id + " not found"));
    }
}
