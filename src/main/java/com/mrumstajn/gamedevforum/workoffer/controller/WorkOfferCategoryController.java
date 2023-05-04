package com.mrumstajn.gamedevforum.workoffer.controller;

import com.mrumstajn.gamedevforum.workoffer.dto.request.CreateWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.EditWorkOfferCategoryRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferCategoryRequestPageable;
import com.mrumstajn.gamedevforum.workoffer.dto.response.WorkOfferCategoryResponse;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferCategory;
import com.mrumstajn.gamedevforum.workoffer.service.command.WorkOfferCategoryCommandService;
import com.mrumstajn.gamedevforum.workoffer.service.query.WorkOfferCategoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work-offer-categories")
public class WorkOfferCategoryController {
    private final WorkOfferCategoryQueryService offerCategoryQueryService;

    private final WorkOfferCategoryCommandService offerCategoryCommandService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<WorkOfferCategoryResponse> create(@RequestBody @Valid CreateWorkOfferCategoryRequest request){
        WorkOfferCategory newCategory = offerCategoryCommandService.create(request);

        return ResponseEntity.created(URI.create("/work-offer-categories/" + newCategory.getId()))
                .body(modelMapper.map(newCategory, WorkOfferCategoryResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOfferCategoryResponse> edit(@PathVariable Long id, @RequestBody @Valid EditWorkOfferCategoryRequest request){
        return ResponseEntity.ok(modelMapper.map(offerCategoryCommandService.edit(id, request), WorkOfferCategoryResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        offerCategoryCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<WorkOfferCategoryResponse>> searchPageable(@RequestBody @Valid SearchWorkOfferCategoryRequestPageable request){
        return ResponseEntity.ok(offerCategoryQueryService.search(request)
                .map(match -> modelMapper.map(match, WorkOfferCategoryResponse.class)));
    }


}
