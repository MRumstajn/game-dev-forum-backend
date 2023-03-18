package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.dto.response.SectionResponse;
import com.mrumstajn.gamedevforum.entity.Section;
import com.mrumstajn.gamedevforum.service.command.SectionCommandService;
import com.mrumstajn.gamedevforum.service.query.SectionQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionController {

    private final SectionQueryService sectionQueryService;

    private final SectionCommandService sectionCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SectionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(sectionQueryService.getById(id), SectionResponse.class));
    }

    @PostMapping
    public ResponseEntity<SectionResponse> create(@RequestBody @Valid CreateSectionRequest request) {
        Section newSection = sectionCommandService.create(request);

        return ResponseEntity.created(URI.create("/sections/" + newSection.getId()))
                .body(modelMapper.map(newSection, SectionResponse.class));
    }
}
