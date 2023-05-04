package com.mrumstajn.gamedevforum.workoffer.controller;

import com.mrumstajn.gamedevforum.workoffer.dto.request.CreateWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.EditWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.SearchWorkOfferRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.request.WorkOfferAverageAndTotalRatingRequest;
import com.mrumstajn.gamedevforum.workoffer.dto.response.WorkOfferAverageAndTotalRatingResponse;
import com.mrumstajn.gamedevforum.workoffer.dto.response.WorkOfferRatingResponse;
import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferRating;
import com.mrumstajn.gamedevforum.workoffer.service.command.WorkOfferRatingCommandService;
import com.mrumstajn.gamedevforum.workoffer.service.query.WorkOfferRatingQueryService;
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
    public ResponseEntity<WorkOfferRatingResponse> create(@RequestBody @Valid CreateWorkOfferRatingRequest request) {
        WorkOfferRating newRating = ratingCommandService.create(request);

        return ResponseEntity.created(URI.create("/work-offer-ratings/" + newRating.getId())).body(modelMapper.map(newRating, WorkOfferRatingResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOfferAverageAndTotalRatingResponse> edit(@PathVariable Long id, @RequestBody @Valid EditWorkOfferRatingRequest request) {
        ratingCommandService.edit(id, request);

        WorkOfferRating rating = workOfferRatingQueryService.getById(id);

        return ResponseEntity.ok(generateAverageAndTotalResponse(rating.getWorkOfferId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkOfferAverageAndTotalRatingResponse> delete(@PathVariable Long id) {
        WorkOfferRating rating = workOfferRatingQueryService.getById(id);

        ratingCommandService.deleteForWorkOfferAndCurrentUser(rating.getWorkOfferId());

        WorkOfferAverageAndTotalRatingResponse response = generateAverageAndTotalResponse(rating.getWorkOfferId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/average-and-total")
    public ResponseEntity<List<WorkOfferAverageAndTotalRatingResponse>> getAverageAndTotalForAll(@RequestBody @Valid WorkOfferAverageAndTotalRatingRequest request) {
        return ResponseEntity.ok(request.getWorkOfferIds().stream().map(this::generateAverageAndTotalResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOfferRatingResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(workOfferRatingQueryService.getById(id), WorkOfferRatingResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<List<WorkOfferRatingResponse>> search(@RequestBody @Valid SearchWorkOfferRatingRequest request) {
        return ResponseEntity.ok(workOfferRatingQueryService.search(request).stream().map(rating -> modelMapper.map(rating, WorkOfferRatingResponse.class)).toList());
    }

    private WorkOfferAverageAndTotalRatingResponse generateAverageAndTotalResponse(Long workOfferId){
        WorkOfferAverageAndTotalRatingResponse response = new WorkOfferAverageAndTotalRatingResponse();
        response.setAverageRating(workOfferRatingQueryService.getAverageRating(workOfferId));
        response.setTotalRatings(workOfferRatingQueryService.getTotalRatings(workOfferId));
        response.setWorkOfferId(workOfferId);

        return response;
    }
}
