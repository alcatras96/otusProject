package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.CategoryViewModel;

import java.util.List;

public interface CategoryService {
    List<CategoryViewModel> findAll();
}
