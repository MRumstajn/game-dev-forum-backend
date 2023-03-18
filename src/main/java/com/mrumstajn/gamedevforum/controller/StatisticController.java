package com.mrumstajn.gamedevforum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.dto.request.CategoryStatisticRequest;
import com.mrumstajn.gamedevforum.dto.response.CategoryStatisticResponse;
import com.mrumstajn.gamedevforum.dto.response.CategoryResponse;
import com.mrumstajn.gamedevforum.dto.response.OverallStatisticResponse;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {

    private final CategoryQueryService categoryQueryService;

    private final ForumThreadQueryService threadQueryService;

    private final PostQueryService postQueryService;

    private final ForumUserQueryService forumUserQueryService;

    private final ObjectMapper mapper;

    @GetMapping("/popular-categories")
    public ResponseEntity<List<CategoryResponse>> getPopularCategories() {
        return ResponseEntity.ok(categoryQueryService.getTopNSortedByThreadCount(3).stream()
                .map(category -> mapper.convertValue(category, CategoryResponse.class)).toList());
    }

    @GetMapping("/overall-statistics")
    public ResponseEntity<OverallStatisticResponse> getOverallStatistics() {
        OverallStatisticResponse overallStatisticResponse = new OverallStatisticResponse();
        overallStatisticResponse.setCategoryCount(categoryQueryService.getTotalCount());
        overallStatisticResponse.setThreadCount(threadQueryService.getTotalCount());
        overallStatisticResponse.setPostCount(postQueryService.getTotalCount());
        overallStatisticResponse.setUserCount(forumUserQueryService.getTotalCount());

        return ResponseEntity.ok(overallStatisticResponse);
    }

    @PostMapping("/category-statistics")
    public ResponseEntity<List<CategoryStatisticResponse>> getStatistic(@RequestBody @Valid CategoryStatisticRequest request) {
        List<CategoryStatisticResponse> categoryStatisticResponses = request.getCategoryIds().stream().map(categoryId -> {
            CategoryStatisticResponse response = new CategoryStatisticResponse();
            response.setCategoryId(categoryId);
            response.setThreadCount(threadQueryService.getTotalCountByCategoryId(categoryId));

            return response;
        }).toList();

        return ResponseEntity.ok(categoryStatisticResponses);
    }
}
