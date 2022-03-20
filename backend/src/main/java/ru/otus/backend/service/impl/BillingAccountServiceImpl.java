package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.repository.BillingAccountRepository;
import ru.otus.backend.service.api.BillingAccountService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Component
public class BillingAccountServiceImpl implements BillingAccountService {

    private final BillingAccountRepository repository;

    @Override
    public BillingAccount saveBillingAccount(BillingAccount billingAccount) {
        return this.repository.save(billingAccount);
    }

    @Override
    public Iterable<BillingAccount> saveBillingAccounts(Iterable<BillingAccount> billingAccounts) {
        return repository.saveAll(billingAccounts);
    }

    @Override
    public void addMoneyToBillingAccounts(Map<Long, Integer> moneyToAddPerBillingAccount) {
        moneyToAddPerBillingAccount.forEach((billingAccountId, moneyToAdd) -> repository.addMonetToBillingAccount(billingAccountId, moneyToAdd));
    }

    @Override
    public void deleteBillingAccountById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<BillingAccount> getBillingAccountById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<BillingAccount> getBillingAccountsById(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
