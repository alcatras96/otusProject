package ru.otus.apigateway.service.impl;

import ru.otus.apigateway.model.view.RoleViewModel;
import ru.otus.apigateway.service.api.RoleDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleDataService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public List<RoleViewModel> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        RoleViewModel[] roleViewModelResponse = restTemplate.getForObject(backendServerUrl + "/api/roles/", RoleViewModel[].class);
        return roleViewModelResponse == null ? Collections.emptyList() : Arrays.asList(roleViewModelResponse);
    }

    @Override
    public RoleViewModel getRoleById(Long id) {
        return null;
    }

}