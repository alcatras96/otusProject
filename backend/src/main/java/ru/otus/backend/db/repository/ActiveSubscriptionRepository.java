package ru.otus.backend.db.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.ActiveSubscription;

import java.util.List;

@Repository
public interface ActiveSubscriptionRepository extends CrudRepository<ActiveSubscription, Long> {

    @Query(value = """
            select a.id             as active_subscription_id,
                   a.customer_id    as active_subscription_customer_id,
                   a.last_edit_date as active_subscription_last_edit_date,
                   a.quantity       as active_subscription_quantity,
                   s.id             as subscription_id,
                   s.name           as subscription_name,
                   s.price          as subscription_price,
                   s.name           as subscription_name,
                   s.image_url      as subscription_image_url,
                   s.description    as subscription_description
            from active_subscriptions a
                     inner join subscriptions s on s.id = a.subscription_id
            where a.customer_id = :customer_id
                                                        """,
            resultSetExtractorRef = "activeSubscriptionsByCustomerIdExtractor")
    List<ActiveSubscription> findByCustomerId(@Param("customer_id") Long id);


    @Modifying
    @Query("update active_subscriptions set quantity = :quantity, last_edit_date = :last_edit_date where id = :id")
    void saveSubscriptionQuantityAndLastEditDate(@Param("id") Long id, @Param("last_edit_date") Long lastEditDate,
                                                 @Param("quantity") Integer quantity);

    @Query(value = """
            select a_s.id             as active_subscription_id,
                   a_s.last_edit_date as active_subscription_last_edit_date,
                   a_s.quantity       as active_subscription_quantity,
                   s.price            as subscription_price,
                   b.id               as owner_billing_account_id,
                   b.balance          as owner_billing_account_balance,
                   b2.id              as customer_billing_account_id,
                   b2.balance         as customer_billing_account_balance,
                   c.id               as customer_id,
                   c.status_id        as customer_status_id
            from active_subscriptions a_s
                     inner join subscriptions s on s.id = a_s.subscription_id
                     inner join owners o on o.id = s.owner_id
                     inner join billing_accounts b on b.id = o.billing_account_id
                     inner join customers c on a_s.customer_id = c.id
                     inner join billing_accounts b2 on b2.id = c.billing_account_id
                                                                                        """,
            resultSetExtractorRef = "allActiveSubscriptionsForRecalculationExtractor")
    List<ActiveSubscription> findAllForRecalculation();
}
