package ru.otus.apigateway.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusViewModel {

    private Long id;
    private String name;
}
