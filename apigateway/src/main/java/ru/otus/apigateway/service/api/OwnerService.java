package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;

import java.util.Optional;

public interface OwnerService {
    Content<OwnerViewModel> getAll(int page, int size);

    Optional<OwnerViewModel> getOwnerById(Long id);

    OwnerViewModel getOwnerByUserId(Long id);

    OwnerViewModel saveOwner(OwnerViewModel owner);

    void saveEditedOwner(OwnerViewModel owner);

    void deleteOwner(Long id);

    void updateOwnerDetails(OwnerViewModel owner);
}
