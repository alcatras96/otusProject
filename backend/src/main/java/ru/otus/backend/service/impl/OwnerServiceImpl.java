package ru.otus.backend.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.repository.OwnerRepository;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.OwnerService;
import ru.otus.backend.service.api.SubscriptionService;
import ru.otus.backend.service.api.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OwnerServiceImpl implements OwnerService {

    private final UserService userService;
    private final OwnerRepository ownerRepository;
    private final BillingAccountService billingAccountService;
    private final SubscriptionService subscriptionService;

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Iterable<Owner> getOwnersById(Iterable<Long> ids) {
        return ownerRepository.findAllById(ids);
    }

    @Transactional
    @Override
    public Owner saveWithBillingAccount(Owner owner) {
        BillingAccount billingAccount = owner.getBillingAccount();
        billingAccount.setBalance(0);
        billingAccountService.saveBillingAccount(billingAccount);
        owner.setBillingAccountId(billingAccount.getId());
        return saveOwner(owner);
    }

    @Override
    public Iterable<Owner> getAllOwners(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        if (pageOptional.isPresent() && sizeOptional.isPresent()) {
            Integer page = pageOptional.get();
            Integer size = sizeOptional.get();
            Page<Owner> owners = ownerRepository.findAll(PageRequest.of(page, size));

            List<Long> userIds = owners.getContent().stream().map(Owner::getUserId).collect(Collectors.toList());
            List<User> users = Lists.newArrayList(userService.getAllUsersById(userIds));

            owners.forEach(owner -> {
                User user = users.stream()
                        .filter(usr -> usr.getId().equals(owner.getUserId()))
                        .findAny()
                        .orElseThrow();

                owner.setUser(user);
            });

            return owners;
        } else {
            return ownerRepository.findAll();
        }
    }

    @Override
    public void updateOwnerDetails(Owner owner) {
        Owner ownerForUpdate = ownerRepository.findById(owner.getId()).orElseThrow();
        ownerForUpdate.setName(owner.getName());
        ownerRepository.save(ownerForUpdate);

        userService.updateUserDetails(owner.getUser());
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
        Owner ownerForDelete = getOwnerById(id).orElseThrow();
        subscriptionService.deleteSubscriptionsByOwnerId(id);
        Long UserId = ownerForDelete.getUserId();
        Long billingAccountId = ownerForDelete.getBillingAccountId();
        ownerRepository.delete(ownerForDelete);
        userService.deleteUser(UserId);
        if (billingAccountId != null) {
            billingAccountService.deleteBillingAccountById(billingAccountId);
        }
    }

    @Override
    public Optional<Owner> findByUserId(Long id) {
        return ownerRepository.findByUserId(id);
    }
}
