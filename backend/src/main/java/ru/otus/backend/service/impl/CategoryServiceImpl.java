package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Category;
import ru.otus.backend.db.repository.CategoryRepository;
import ru.otus.backend.service.api.CategoryService;

@AllArgsConstructor
@Component
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public Iterable<Category> findAll() {
        return repository.findAll();
    }
}
