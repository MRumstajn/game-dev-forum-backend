package com.mrumstajn.gamedevforum.service.command;

import com.mrumstajn.gamedevforum.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.entity.Section;

public interface SectionCommandService {

    Section create(CreateSectionRequest request);
}
