package com.mrumstajn.gamedevforum.section.service.query;

import com.mrumstajn.gamedevforum.section.dto.request.SearchSectionRequest;
import com.mrumstajn.gamedevforum.section.entity.Section;

import java.util.List;

public interface SectionQueryService {
    Section getById(Long id);

    List<Section> search(SearchSectionRequest request);
}
