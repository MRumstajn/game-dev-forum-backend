package com.mrumstajn.gamedevforum.dto.request;

import lombok.Getter;
import lombok.Setter;
import net.croz.nrich.search.api.model.sort.SortDirection;
import net.croz.nrich.search.api.model.sort.SortProperty;
import net.croz.nrich.search.api.request.BaseSortablePageableRequest;

import java.util.List;

@Getter
@Setter
public class SearchCategoriesRequestPageable extends BaseSortablePageableRequest {
    private Long sectionId;

    private String title;

    public SearchCategoriesRequestPageable(){
        setPageSize(10);
        setPageNumber(0);
        setSortPropertyList(List.of(new SortProperty("id", SortDirection.ASC)));
    }
}
