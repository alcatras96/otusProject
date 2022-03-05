package ru.otus.apigateway.model.backend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.apigateway.model.view.CategoryViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    private Long id;
    private Integer price;
    private String name;
    private String imageUrl;
    private String description;
    private Long ownerId;
    private Long categoryId;

    private OwnerViewModel owner;
    private CategoryViewModel category;
}


