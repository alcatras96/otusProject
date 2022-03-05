package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> getRoleById(Long id);

    Iterable<Role> getAllRoles();
}
