package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import ru.otus.apigateway.model.view.CategoryViewModel;
import ru.otus.apigateway.service.api.CategoryDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryDataService categoryDataService;

    @RequestMapping
    public ResponseEntity<List<CategoryViewModel>> getAllCategories() {
        return ResponseEntity.ok(categoryDataService.findAll());
    }

}
