package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Long id);

    Iterable<User> getAllUsers();

    User saveUser(User user);

    User updateUserDetails(User user);

    void deleteUser(Long id);

    Optional<User> findByLogin(String login);

    Iterable<User> getAllUsersById(List<Long> ids);
}
