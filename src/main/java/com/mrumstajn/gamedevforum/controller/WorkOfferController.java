package com.mrumstajn.gamedevforum.controller;

import com.mrumstajn.gamedevforum.dto.request.CreateWorkOfferRequest;
import com.mrumstajn.gamedevforum.dto.request.EditWorkOfferRequest;
import com.mrumstajn.gamedevforum.dto.request.SearchWorkOfferRequestPageable;
import com.mrumstajn.gamedevforum.dto.response.WorkOfferResponse;
import com.mrumstajn.gamedevforum.entity.WorkOffer;
import com.mrumstajn.gamedevforum.service.command.WorkOfferCommandService;
import com.mrumstajn.gamedevforum.service.query.WorkOfferQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work-offers")
public class WorkOfferController {
    private final WorkOfferQueryService workOfferQueryService;

    private final WorkOfferCommandService workOfferCommandService;

    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<WorkOfferResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(modelMapper.map(workOfferQueryService.getById(id), WorkOfferResponse.class));
    }

    @PostMapping
    public ResponseEntity<WorkOfferResponse> create(@RequestBody @Valid CreateWorkOfferRequest request){
        WorkOffer newWorkOffer = workOfferCommandService.create(request);

        return ResponseEntity.created(URI.create("/work-offers/" + newWorkOffer.getId()))
                .body(modelMapper.map(newWorkOffer, WorkOfferResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkOfferResponse> edit(@PathVariable Long id, @RequestBody @Valid EditWorkOfferRequest request){
        return ResponseEntity.ok(modelMapper.map(workOfferCommandService.edit(id, request), WorkOfferResponse.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        workOfferCommandService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<WorkOfferResponse>> search(@RequestBody @Valid SearchWorkOfferRequestPageable requestPageable){
        return ResponseEntity.ok(workOfferQueryService.searchPageable(requestPageable)
                .map(result -> modelMapper.map(result, WorkOfferResponse.class)));
    }
}
