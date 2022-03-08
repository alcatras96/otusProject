package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;

import java.util.List;

public interface BillingAccountService {
    void createBillingAccount(BillingAccountViewModel ba);

    void saveBillingAccount(BillingAccountViewModel ba);

    List<BillingAccountViewModel> getAllBillingAccounts();

    BillingAccountViewModel getBillingAccountById(Long id);

    OwnerViewModel saveOwnerBillingAccount(OwnerViewModel owner);

    OwnerViewModel saveCustomerBillingAccount(CustomerViewModel customer);
}
