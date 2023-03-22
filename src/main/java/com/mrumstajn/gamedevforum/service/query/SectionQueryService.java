package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.dto.request.SearchSectionRequest;
import com.mrumstajn.gamedevforum.entity.Section;

import java.util.List;

public interface SectionQueryService {
    Section getById(Long id);

    List<Section> search(SearchSectionRequest request);
}
