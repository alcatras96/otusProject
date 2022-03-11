package ru.otus.apigateway.model.view;

import lombok.Data;

@Data
public class Content<T> {

    private T []content;
    private int totalElements;
}
