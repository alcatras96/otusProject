package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.Category;

public interface CategoryService {
    Iterable<Category> findAll();
}
