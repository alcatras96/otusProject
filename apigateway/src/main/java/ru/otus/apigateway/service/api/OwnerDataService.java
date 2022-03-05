package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;

import java.util.Optional;

public interface OwnerDataService {
    Content<OwnerViewModel> getAll(int page, int size);

    Optional<OwnerViewModel> getOwnerById(Long id);

    OwnerViewModel getOwnerByUserId(Long id);

    OwnerViewModel saveOwner(OwnerViewModel owner);

    void saveEditedOwner(OwnerViewModel owner);

    OwnerViewModel saveOwnerBa(OwnerViewModel owner);

    void deleteOwner(Long id);
}
