package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.backend.constants.Constants;
import ru.otus.backend.db.entity.*;
import ru.otus.backend.service.api.ActiveSubscriptionService;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.CustomerService;
import ru.otus.backend.service.api.SubtractionService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class SubtractionServiceImpl implements SubtractionService {

    private static final int CYCLE_TIME = 10000;

    private final ActiveSubscriptionService activeSubscriptionService;
    private final CustomerService customerService;
    private final BillingAccountService billingAccountService;

    @Getter
    private static final class EntitiesToEdit {
        private final Set<BillingAccount> billingAccountsToUpdate = new HashSet<>();
        private final Set<ActiveSubscription> activeSubscriptionsToUpdate = new HashSet<>();
        private final Set<ActiveSubscription> activeSubscriptionsToDelete = new HashSet<>();
        private final Set<Customer> customersToUpdate = new HashSet<>();

        public void addBillingAccountToUpdate(BillingAccount billingAccount) {
            billingAccountsToUpdate.add(billingAccount);
        }

        public void addActiveSubscriptionToUpdate(ActiveSubscription activeSubscription) {
            activeSubscriptionsToUpdate.add(activeSubscription);
        }

        public void addActiveSubscriptionToDelete(ActiveSubscription activeSubscription) {
            activeSubscriptionsToDelete.add(activeSubscription);
        }

        public void addCustomerToUpdate(Customer customer) {
            customersToUpdate.add(customer);
        }
    }

    @Override
    @Scheduled(fixedDelay = CYCLE_TIME)
    public void subtract() {
        List<ActiveSubscription> activeSubscriptions = activeSubscriptionService.findAllForRecalculation();
        if (!activeSubscriptions.isEmpty()) {
            Map<Customer, List<ActiveSubscription>> activeSubscriptionsByCustomerId = activeSubscriptions.stream().collect(
                    Collectors.groupingBy(ActiveSubscription::getCustomer, HashMap::new, Collectors.toCollection(ArrayList::new))
            );

            var entitiesToEdit = new EntitiesToEdit();

            activeSubscriptionsByCustomerId.keySet().forEach(customer -> {
                List<ActiveSubscription> activeSubscriptionsForCurrentCustomer = activeSubscriptionsByCustomerId.get(customer);
                for (ActiveSubscription activeSubscription : activeSubscriptionsForCurrentCustomer) {
                    long newEditDate = System.currentTimeMillis();
                    int quantityForSubtract = (int) (newEditDate - activeSubscription.getLastEditDate()) / CYCLE_TIME;
                    if (quantityForSubtract > 0) {
                        if (customer.getStatus() == Status.VALID) {
                            activeSubscription.setLastEditDate(newEditDate);

                            int quantityAfterSubtract = activeSubscription.getQuantity() - quantityForSubtract;
                            if (quantityAfterSubtract < 0) {
                                entitiesToEdit.addActiveSubscriptionToUpdate(activeSubscription);
                            } else {
                                recalculateActiveSubscriptionQuantity(entitiesToEdit, activeSubscription, quantityAfterSubtract);
                                recalculateCustomerAndOwnerBillingAccountBalance(entitiesToEdit, activeSubscription, quantityForSubtract);
                            }

                            if (customer.getBillingAccount().getBalance() < Constants.THRESHOLD) {
                                customer.setStatus(Status.BLOCKED);
                                entitiesToEdit.addCustomerToUpdate(customer);
                            }
                        } else {
                            entitiesToEdit.addActiveSubscriptionToUpdate(activeSubscription);
                        }
                    }
                }
            });

            processEntitiesToEdit(entitiesToEdit);

            log.info("------------------------------------");
            log.info("Cycle is done.");
            log.info("------------------------------------");
        }
    }

    private void processEntitiesToEdit(EntitiesToEdit entitiesToEdit) {
        activeSubscriptionService.deleteActiveSubscriptions(entitiesToEdit.getActiveSubscriptionsToDelete());
        activeSubscriptionService.saveSubscriptionQuantityAndLastEditDate(entitiesToEdit.getActiveSubscriptionsToUpdate());
        billingAccountService.saveBillingAccountsBalance(entitiesToEdit.getBillingAccountsToUpdate());
        customerService.saveCustomersStatus(entitiesToEdit.getCustomersToUpdate());
    }

    private void recalculateActiveSubscriptionQuantity(EntitiesToEdit entitiesToEdit, ActiveSubscription activeSubscription, int quantityAfterSubtract) {
        if (quantityAfterSubtract > 0) {
            activeSubscription.setQuantity(quantityAfterSubtract);
            entitiesToEdit.addActiveSubscriptionToUpdate(activeSubscription);
        } else {
            entitiesToEdit.addActiveSubscriptionToDelete(activeSubscription);
        }
    }

    private void recalculateCustomerAndOwnerBillingAccountBalance(EntitiesToEdit entitiesToEdit, ActiveSubscription activeSubscription,
                                                                  int quantityForSubtract) {
        Subscription subscription = activeSubscription.getSubscription();
        BillingAccount customerBillingAccount = activeSubscription.getCustomer().getBillingAccount();
        BillingAccount ownerBillingAccount = subscription.getOwner().getBillingAccount();
        int customerPaymentAmount = quantityForSubtract * subscription.getPrice();
        customerBillingAccount.setBalance(customerBillingAccount.getBalance() - customerPaymentAmount);
        ownerBillingAccount.setBalance(ownerBillingAccount.getBalance() + customerPaymentAmount);
        entitiesToEdit.addBillingAccountToUpdate(ownerBillingAccount);
        entitiesToEdit.addBillingAccountToUpdate(customerBillingAccount);
    }
}
