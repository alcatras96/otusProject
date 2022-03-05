package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.repository.OwnerRepository;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.OwnerService;
import ru.otus.backend.service.api.UserService;

import java.util.Optional;

@AllArgsConstructor
@Component
public class OwnerServiceImpl implements OwnerService {

    private final UserService userService;
    private final OwnerRepository ownerRepository;
    private final BillingAccountService billingAccountService;

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Iterable<Owner> getAllOwners(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        if (pageOptional.isPresent() && sizeOptional.isPresent()) {
            Integer page = pageOptional.get();
            Integer size = sizeOptional.get();
            return ownerRepository.findAll(page * size, size);
        } else {
            return ownerRepository.findAll();
        }
    }

    @Transactional
    @Override
    public Owner saveOwner(Owner owner) {
        User user = owner.getUser();
        user.setRoleId(user.getRole().getId());
        user = userService.saveUser(user);
        owner.setUserId(user.getId());
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        Owner deletedOwner = getOwnerById(id).get();
        Long UserId = deletedOwner.getUser().getId();
        BillingAccount billingAccount = deletedOwner.getBillingAccount();
        if (billingAccount != null) {
            billingAccountService.deleteBa(billingAccount);
        }
        userService.deleteUser(UserId);
    }

    @Override
    public Owner findByUserId(Long id) {
        return ownerRepository.findByUserId(id);
    }
}
