package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.RoleViewModel;

import java.util.List;

public interface RoleDataService {
    List<RoleViewModel> getAll();

    RoleViewModel getRoleById(Long id);
}
