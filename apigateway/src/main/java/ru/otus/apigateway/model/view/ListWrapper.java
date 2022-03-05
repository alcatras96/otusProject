package ru.otus.apigateway.model.view;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ListWrapper<T> {

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<T> listWrapper;
}
