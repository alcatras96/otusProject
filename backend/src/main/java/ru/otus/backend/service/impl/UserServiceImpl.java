package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.repository.UserRepository;
import ru.otus.backend.service.api.UserService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUserDetails(User user) {
        User userForUpdate = getUserById(user.getId()).orElseThrow();
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setLogin(user.getLogin());
        saveUser(userForUpdate);

        return userForUpdate;
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public Iterable<User> getAllUsersById(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
