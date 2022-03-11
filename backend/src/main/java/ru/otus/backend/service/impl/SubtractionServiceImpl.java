package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.backend.constants.Constants;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.service.api.ActiveSubscriptionService;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.CustomerService;
import ru.otus.backend.service.api.SubtractionService;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class SubtractionServiceImpl implements SubtractionService {

    private static final int CYCLE_TIME = 60000;

    private final ActiveSubscriptionService activeSubscriptionService;
    private final CustomerService customerService;
    private final BillingAccountService billingAccountService;

    @Override
    @Scheduled(fixedDelay = CYCLE_TIME)
    public void subtract() {
        Iterable<ActiveSubscription> activeSubscriptions = activeSubscriptionService.getAllActiveSubscriptions();
        for (ActiveSubscription subscription : activeSubscriptions) {
            long newEditedDate = System.currentTimeMillis();
            long deltaTime = newEditedDate - subscription.getLastEditDate();
            int amount = (int) deltaTime / CYCLE_TIME;
            log.info("Difference in time: " + ((Long) deltaTime));
            if (amount > 0) {
                Optional<Customer> customerOptional = customerService.getCustomerById(subscription.getCustomerId());

                if (customerOptional.isPresent()) {
                    Customer customer = customerOptional.get();

                    if (customer.getStatus().getName().equals("valid")) {
                        int subtractMoney = amount * subscription.getSubscription().getPrice();
                        BillingAccount customerBillingAccount = customer.getBillingAccount();
                        Integer balance = customerBillingAccount.getBalance() - subtractMoney;

                        int quantity = subscription.getQuantity() - amount;
                        Owner owner = subscription.getSubscription().getOwner();
                        BillingAccount ownerBillingAccount = owner.getBillingAccount();
                        Integer ownerBalance = ownerBillingAccount.getBalance();

                        if (quantity < 0) {
                            subscription.setLastEditDate(newEditedDate);
                            activeSubscriptionService.saveActiveSubscription(subscription);
                        } else {
                            if (quantity > 0) {
                                subscription.setQuantity(quantity);
                                subscription.setLastEditDate(newEditedDate);
                                activeSubscriptionService.saveActiveSubscription(subscription);
                            } else {
                                activeSubscriptionService.deleteActiveSubscriptionById(subscription.getId());
                            }

                            customerBillingAccount.setBalance(balance);
                            log.info("Amount: " + customerBillingAccount.getBalance().toString());
                            ownerBalance += subtractMoney;
                            log.info("Owner amount: " + ownerBalance);

                            ownerBillingAccount.setBalance(ownerBalance);
                            billingAccountService.saveBillingAccount(ownerBillingAccount);
                            billingAccountService.saveBillingAccount(customerBillingAccount);
                        }

                        if (customerBillingAccount.getBalance() < Constants.THRESHOLD) {
                            customerService.updateCustomerStatus(customer.getId(), 2L);
                        }
                    } else {
                        subscription.setLastEditDate(newEditedDate);
                        activeSubscriptionService.saveActiveSubscription(subscription);
                    }
                }
            }
            log.info("------------------------------------");
        }
        log.info("Cycle is done.");
        log.info("------------------------------------");

    }

}
