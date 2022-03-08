package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.Owner;

import java.util.Optional;

public interface OwnerService {

    Optional<Owner> getOwnerById(Long id);

    Owner saveWithBillingAccount(Owner owner);

    Iterable<Owner> getAllOwners(Optional<Integer> pageOptional, Optional<Integer> sizeOptional);

    Owner saveOwner(Owner owner);

    void deleteOwner(Long id);

    Owner findByUserId(Long id);

}
