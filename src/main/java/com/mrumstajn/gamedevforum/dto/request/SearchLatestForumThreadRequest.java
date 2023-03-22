package com.mrumstajn.gamedevforum.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.model.sort.SortDirection;
import net.croz.nrich.search.api.model.sort.SortProperty;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SearchLatestForumThreadRequest extends BaseSortablePageableRequest {
    @NotNull
    private Long categoryId;
}
