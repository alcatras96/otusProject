package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Status;
import ru.otus.backend.db.repository.StatusRepository;
import ru.otus.backend.service.api.StatusService;

import java.util.Optional;

@AllArgsConstructor
@Component
public class StatusServiceImpl implements StatusService {

    private final StatusRepository repository;

    @Override
    public Optional<Status> getStatusById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Status> getAllStatuses() {
        return repository.findAll();
    }
}
