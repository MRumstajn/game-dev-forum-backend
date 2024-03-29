package com.mrumstajn.gamedevforum.category.controller;

import com.mrumstajn.gamedevforum.category.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.category.dto.request.EditCategoryRequest;
import com.mrumstajn.gamedevforum.category.dto.request.SearchCategoriesRequestPageable;
import com.mrumstajn.gamedevforum.category.dto.response.CategoryResponse;
import com.mrumstajn.gamedevforum.category.entity.Category;
import com.mrumstajn.gamedevforum.category.service.command.CategoryCommandService;
import com.mrumstajn.gamedevforum.category.service.query.CategoryQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<Page<CategoryResponse>> search(@RequestBody @Valid SearchCategoriesRequestPageable request){
        return ResponseEntity.ok(categoryQueryService.search(request)
                .map(category -> modelMapper.map(category, CategoryResponse.class)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request){
        Category newCategory = categoryCommandService.create(request);

        return ResponseEntity.created(URI.create("/categories/" + newCategory.getId()))
                .body(modelMapper.map(newCategory, CategoryResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> edit(@PathVariable Long id, @RequestBody @Valid EditCategoryRequest request){
        return ResponseEntity.ok(modelMapper.map(categoryCommandService.edit(id, request), CategoryResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
