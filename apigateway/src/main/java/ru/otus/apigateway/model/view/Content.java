package ru.otus.apigateway.model.view;

import lombok.Data;

@Data
public class Content<T> {

    private T []content;
    private PageableImpl pageable;
    private boolean last;
    private int totalPages;
    private int totalElements;
    private int size;
    private int number;
    private SortImpl sort;
    private int numberOfElements;
    private boolean first;
}
