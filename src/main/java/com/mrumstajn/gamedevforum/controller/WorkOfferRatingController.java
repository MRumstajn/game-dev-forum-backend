package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.dto.request.WorkOfferAverageAndTotalRatingRequest;
import com.mrumstajn.gamedevforum.dto.response.WorkOfferAverageAndTotalRatingResponse;
import com.mrumstajn.gamedevforum.dto.response.WorkOfferRatingResponse;
import com.mrumstajn.gamedevforum.entity.WorkOfferRating;
import com.mrumstajn.gamedevforum.service.command.WorkOfferRatingCommandService;
import com.mrumstajn.gamedevforum.service.query.WorkOfferRatingQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work-offer-ratings")
public class WorkOfferRatingController {
    private final WorkOfferRatingQueryService workOfferRatingQueryService;

    private final WorkOfferRatingCommandService ratingCommandService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<WorkOfferRatingResponse> create(@RequestBody @Valid CreateWorkOfferRatingRequest request){
        WorkOfferRating newRating = ratingCommandService.create(request);

        return ResponseEntity.created(URI.create("/work-offer-ratings/" + newRating.getId())).body(modelMapper.map(newRating, WorkOfferRatingResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOfferRatingResponse> edit(@PathVariable Long id, @RequestBody @Valid EditWorkOfferRatingRequest request){
        return ResponseEntity.ok(modelMapper.map(ratingCommandService.edit(id, request), WorkOfferRatingResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ratingCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/average-and-total")
    public ResponseEntity<List<WorkOfferAverageAndTotalRatingResponse>> getAverageAndTotalForAll(@RequestBody @Valid WorkOfferAverageAndTotalRatingRequest request){
        List<WorkOfferAverageAndTotalRatingResponse> responses = request.getWorkOfferIds().stream().map(id -> {
            WorkOfferAverageAndTotalRatingResponse response = new WorkOfferAverageAndTotalRatingResponse();
            response.setAverageRating(workOfferRatingQueryService.getAverageRating(id));
            response.setTotalRatings(workOfferRatingQueryService.getTotalRatings(id));
            response.setWorkOfferId(id);
            return response;
        }).toList();

        return ResponseEntity.ok(responses);
    }
}
