package com.mrumstajn.gamedevforum.section.service.command;

import com.mrumstajn.gamedevforum.section.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.section.entity.Section;

public interface SectionCommandService {

    Section create(CreateSectionRequest request);
}
