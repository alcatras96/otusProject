package ru.otus.apigateway.model.view;

import lombok.Data;

@Data
public class PageableImpl {

    private SortImpl sort;
    private Integer offset;
    private Integer pageSize;
    private Integer pageNumber;
    private Boolean paged;
    private Boolean unpaged;
}
