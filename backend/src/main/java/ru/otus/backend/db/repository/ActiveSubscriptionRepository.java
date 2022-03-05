package ru.otus.backend.db.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.ActiveSubscription;

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
    Iterable<ActiveSubscription> findByCustomerId(@Param("customer_id") Long id);
}
