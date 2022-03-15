package ru.otus.apigateway.facade.api;

import ru.otus.apigateway.model.view.BillingAccountViewModel;

public interface BillingAccountFacade {
    BillingAccountViewModel saveBillingAccount(BillingAccountViewModel billingAccountViewModel);

    void addMoneyOnBillingAccount(Long value);
}
