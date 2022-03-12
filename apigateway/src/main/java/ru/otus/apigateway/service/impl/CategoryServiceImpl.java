package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.CategoryViewModel;
import ru.otus.apigateway.service.api.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/categories";

    private final String backendServerUrl;

    public CategoryServiceImpl(@Value("${backend.server.url}") String backendServerUrl) {
        this.backendServerUrl = backendServerUrl;
    }

    @Override
    public List<CategoryViewModel> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, List.class);
    }
}
