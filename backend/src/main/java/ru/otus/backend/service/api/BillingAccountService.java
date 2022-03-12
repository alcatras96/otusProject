package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.BillingAccount;

import java.util.List;
import java.util.Optional;

public interface BillingAccountService {

    BillingAccount saveBillingAccount(BillingAccount billingAccounts);

    Iterable<BillingAccount> saveBillingAccounts(Iterable<BillingAccount> billingAccounts);

    void deleteBillingAccountById(Long id);

    Optional<BillingAccount> getBillingAccountById(Long id);

    Iterable<BillingAccount> getBillingAccountsById(List<Long> ids);
}
