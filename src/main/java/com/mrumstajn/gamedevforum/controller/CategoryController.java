package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchCategoriesRequest;
import com.mrumstajn.gamedevforum.dto.response.CategoryResponse;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.service.command.CategoryCommandService;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryQueryService categoryQueryService;

    private final CategoryCommandService categoryCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(modelMapper.map(categoryQueryService.getById(id), CategoryResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategoryResponse>> search(@RequestBody @Valid SearchCategoriesRequest request){
        return ResponseEntity.ok(categoryQueryService.search(request).stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class)).toList());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request){
        Category newCategory = categoryCommandService.create(request);

        return ResponseEntity.created(URI.create("/categories/" + newCategory.getId()))
                .body(modelMapper.map(newCategory, CategoryResponse.class));
    }
}
