package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CategoryStatisticRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchLatestForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchLatestPostRequest;
import com.mrumstajn.gamedevforum.dto.request.ThreadStatisticRequest;
import com.mrumstajn.gamedevforum.dto.response.*;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.entity.Post;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import com.mrumstajn.gamedevforum.service.query.ForumThreadQueryService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.service.query.PostQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    @GetMapping("/popular-categories")
    public ResponseEntity<List<CategoryResponse>> getPopularCategories() {
        return ResponseEntity.ok(categoryQueryService.getTopNSortedByThreadCount(3).stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class)).toList());
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

            ForumThread latestThreadFromCategory = threadQueryService.getLatest(new SearchLatestForumThreadRequest(categoryId));
            if (latestThreadFromCategory != null) {
                response.setThreadWithLatestActivity(modelMapper.map(latestThreadFromCategory, ForumThreadResponse.class));
            } else {
                response.setThreadWithLatestActivity(null);
            }

            return response;
        }).toList();

        return ResponseEntity.ok(categoryStatisticResponses);
    }

    @PostMapping("/thread-statistics")
    public ResponseEntity<List<ThreadStatisticResponse>> getStatistic(@RequestBody @Valid ThreadStatisticRequest request) {
        List<ThreadStatisticResponse> threadStatisticResponses = request.getThreadIds().stream().map(threadId -> {
            threadQueryService.getById(threadId); // check if thread exists first

            ThreadStatisticResponse response = new ThreadStatisticResponse();
            response.setThreadId(threadId);
            response.setPostCount(postQueryService.getTotalCountByThreadId(threadId));

            Post latestPost = postQueryService.getLatest(new SearchLatestPostRequest(threadId));
            if (latestPost != null) {
                response.setLatestPost(modelMapper.map(latestPost, PostResponse.class));
            } else {
                response.setLatestPost(null);
            }

            return response;
        }).toList();

        return ResponseEntity.ok(threadStatisticResponses);
    }
}
