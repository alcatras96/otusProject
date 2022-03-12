package ru.otus.backend.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.backend.constants.Constants;
import ru.otus.backend.db.entity.*;
import ru.otus.backend.service.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class SubtractionServiceImpl implements SubtractionService {

    private static final int CYCLE_TIME = 10000;

    private final ActiveSubscriptionService activeSubscriptionService;
    private final SubscriptionService subscriptionService;
    private final CustomerService customerService;
    private final BillingAccountService billingAccountService;
    private final OwnerService ownerService;

    @Override
    @Scheduled(fixedDelay = CYCLE_TIME)
    public void subtract() {
        List<ActiveSubscription> activeSubscriptions = Lists.newArrayList(activeSubscriptionService.getAllActiveSubscriptions());
        if (!activeSubscriptions.isEmpty()) {
            List<Subscription> subscriptions = Lists.newArrayList(subscriptionService.getSubscriptionsById(activeSubscriptions.stream()
                    .map(ActiveSubscription::getSubscriptionId).collect(Collectors.toList())));
            activeSubscriptions.forEach(activeSubscription -> activeSubscription.setSubscription(
                            subscriptions.stream()
                                    .filter(subscription -> subscription.getId().equals(activeSubscription.getSubscriptionId()))
                                    .findAny().orElseThrow()
                    )
            );

            List<Customer> customers = Lists.newArrayList(customerService.getCustomersById(activeSubscriptions.stream()
                    .map(ActiveSubscription::getCustomerId).collect(Collectors.toList())));
            List<Owner> owners = Lists.newArrayList(ownerService.getOwnersById(
                    subscriptions.stream()
                            .map(Subscription::getOwnerId)
                            .collect(Collectors.toSet()))
            );

            List<Long> billingAccountIds = customers.stream().map(Customer::getBillingAccountId).collect(Collectors.toList());
            billingAccountIds.addAll(owners.stream().map(Owner::getBillingAccountId).collect(Collectors.toList()));

            List<BillingAccount> billingAccounts = Lists.newArrayList(billingAccountService.getBillingAccountsById(billingAccountIds));
            customers.forEach(customer -> customer.setBillingAccount(
                            billingAccounts.stream()
                                    .filter(billingAccount -> billingAccount.getId().equals(customer.getBillingAccountId()))
                                    .findAny().orElseThrow()
                    )
            );
            owners.forEach(owner -> owner.setBillingAccount(
                            billingAccounts.stream()
                                    .filter(billingAccount -> billingAccount.getId().equals(owner.getBillingAccountId()))
                                    .findAny().orElseThrow()
                    )
            );
            subscriptions.forEach(subscription -> subscription.setOwner(owners.stream().filter(owner -> owner.getId().equals(subscription.getOwnerId())).findAny().orElseThrow()));

            HashMap<Long, ArrayList<ActiveSubscription>> activeSubscriptionsByCustomerId = activeSubscriptions.stream().collect(
                    Collectors.groupingBy(ActiveSubscription::getCustomerId, HashMap::new, Collectors.toCollection(ArrayList::new))
            );

            ArrayList<BillingAccount> billingAccountsToUpdate = new ArrayList<>();
            ArrayList<ActiveSubscription> activeSubscriptionsToUpdate = new ArrayList<>();
            ArrayList<ActiveSubscription> activeSubscriptionsToDelete = new ArrayList<>();
            ArrayList<Customer> customersToUpdate = new ArrayList<>();
            Set<Long> customerIds = activeSubscriptionsByCustomerId.keySet();
            customerIds.forEach(customerId -> {
                ArrayList<ActiveSubscription> activeSubscriptionsForCurrentCustomer = activeSubscriptionsByCustomerId.get(customerId);
                Customer customer = customers.stream()
                        .filter(cust -> customerId.equals(cust.getId()))
                        .findAny()
                        .orElseThrow();
                for (ActiveSubscription activeSubscription : activeSubscriptionsForCurrentCustomer) {
                    long newEditedDate = System.currentTimeMillis();
                    long deltaTime = newEditedDate - activeSubscription.getLastEditDate();
                    int amount = (int) deltaTime / CYCLE_TIME;
                    log.info("Difference in time: " + ((Long) deltaTime));
                    if (amount > 0) {

                        if (customer.getStatusId().equals(1L)) {
                            int subtractMoney = amount * activeSubscription.getSubscription().getPrice();
                            BillingAccount customerBillingAccount = customer.getBillingAccount();
                            Integer balance = customerBillingAccount.getBalance() - subtractMoney;

                            int quantity = activeSubscription.getQuantity() - amount;
                            Owner owner = activeSubscription.getSubscription().getOwner();
                            BillingAccount ownerBillingAccount = owner.getBillingAccount();
                            Integer ownerBalance = ownerBillingAccount.getBalance();

                            if (quantity < 0) {
                                activeSubscription.setLastEditDate(newEditedDate);
                                activeSubscriptionsToUpdate.add(activeSubscription);
                            } else {
                                if (quantity > 0) {
                                    activeSubscription.setQuantity(quantity);
                                    activeSubscription.setLastEditDate(newEditedDate);
                                    activeSubscriptionsToUpdate.add(activeSubscription);
                                } else {
                                    activeSubscriptionsToDelete.add(activeSubscription);
                                }

                                customerBillingAccount.setBalance(balance);
                                log.info("Amount: " + customerBillingAccount.getBalance().toString());
                                ownerBalance += subtractMoney;
                                log.info("Owner amount: " + ownerBalance);

                                ownerBillingAccount.setBalance(ownerBalance);
                                billingAccountsToUpdate.add(ownerBillingAccount);
                                billingAccountsToUpdate.add(customerBillingAccount);
                            }

                            if (customerBillingAccount.getBalance() < Constants.THRESHOLD) {
                                customer.setStatusId(2L);
                                customersToUpdate.add(customer);
                            }
                        } else {
                            activeSubscription.setLastEditDate(newEditedDate);
                            activeSubscriptionsToUpdate.add(activeSubscription);
                        }

                    }
                    log.info("------------------------------------");
                }
            });
            activeSubscriptionService.deleteActiveSubscriptions(activeSubscriptionsToDelete);
            activeSubscriptionService.saveActiveSubscriptions(activeSubscriptionsToUpdate);
            billingAccountService.saveBillingAccounts(billingAccountsToUpdate);
            customerService.saveCustomers(customersToUpdate);

            log.info("Cycle is done.");
            log.info("------------------------------------");
        }
    }

}
