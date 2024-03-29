package com.mrumstajn.gamedevforum.section.service.query.impl;

import com.mrumstajn.gamedevforum.section.dto.request.SearchSectionRequest;
import com.mrumstajn.gamedevforum.section.entity.Section;
import com.mrumstajn.gamedevforum.section.repository.SectionRepository;
import com.mrumstajn.gamedevforum.section.service.query.SectionQueryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.croz.nrich.search.api.model.SearchConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionQueryServiceImpl implements SectionQueryService {

    private final SectionRepository sectionRepository;

    @Override
    public Section getById(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Section with id " + id + " not found"));
    }

    @Override
    public List<Section> search(SearchSectionRequest request) {
        SearchConfiguration<Section, Section, SearchSectionRequest> searchConfiguration = SearchConfiguration.<Section, Section, SearchSectionRequest>builder()
                .resolvePropertyMappingUsingPrefix(true)
                .resultClass(Section.class)
                .build();

        return sectionRepository.findAll(request, searchConfiguration);
    }
}
