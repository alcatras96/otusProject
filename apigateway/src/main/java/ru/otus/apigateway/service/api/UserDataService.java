package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.UserViewModel;

import java.util.List;
import java.util.Optional;

public interface UserDataService {
    List<UserViewModel> getAll();

    Optional<UserViewModel> getUserById(Long id);

    UserViewModel findByLogin(String name);
}
