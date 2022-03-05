package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.Status;

import java.util.Optional;

public interface StatusService {
    Optional<Status> getStatusById(Long id);

    Iterable<Status> getAllStatuses();
}
