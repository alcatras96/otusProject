package ru.otus.apigateway.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    VALID("valid"),
    BLOCKED("blocked");

    private final String name;
}
