package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.UserViewModel;

import java.util.List;

public interface UserService {
    List<UserViewModel> getAll();

    UserViewModel getUserById(Long id);

    UserViewModel getUserByLogin(String name);

    UserViewModel getCurrentUserByLogin();
}
