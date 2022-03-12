package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;

public interface BillingAccountService {
    void saveBillingAccount(BillingAccountViewModel ba);

    OwnerViewModel saveOwnerBillingAccount(OwnerViewModel owner);

    OwnerViewModel saveCustomerBillingAccount(CustomerViewModel customer);
}
