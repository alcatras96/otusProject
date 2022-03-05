package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Role;
import ru.otus.backend.db.repository.RoleRepository;
import ru.otus.backend.service.api.RoleService;

import java.util.Optional;

@AllArgsConstructor
@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Optional<Role> getRoleById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Role> getAllRoles() {
        return repository.findAll();
    }

}
