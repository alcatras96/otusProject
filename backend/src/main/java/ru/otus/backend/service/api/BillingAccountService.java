package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.BillingAccount;

import java.util.Optional;

public interface BillingAccountService {

    BillingAccount saveBillingAccount(BillingAccount ba);

    void deleteBa(BillingAccount ba);

    Optional<BillingAccount> getBillingAccountById(Long id);

    Iterable<BillingAccount> getAllBillingAccounts();
}
