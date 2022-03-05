package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.BillingAccountViewModel;

import java.util.List;

public interface BillingAccountService {
    void createBillingAccount(BillingAccountViewModel ba);

    void saveBillingAccount(BillingAccountViewModel ba);

    List<BillingAccountViewModel> getAllBillingAccounts();

    BillingAccountViewModel getBillingAccountById(Long id);
}
