package ru.otus.backend.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.entity.Subscription;

@Repository
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {

    @Query(value = """
            select s.id          as subscription_id,
                   s.name        as subscription_name,
                   s.price       as subscription_price,
                   s.name        as subscription_name,
                   s.image_url   as subscription_image_url,
                   s.description as subscription_description,
                   o.id          as owner_id,
                   c.id          as category_id,
                   c.name        as category_name
            from subscriptions s
                     inner join owners o on o.id = s.owner_id
                     inner join categories c on c.id = s.category_id
            where s.owner_id = :owner_id
                                                        """,
            resultSetExtractorRef = "subscriptionsByOwnerIdExtractor")
    Iterable<Subscription> findByOwnerId(@Param("owner_id") Long id);

    Page<Subscription> findByNameContaining(String name, Pageable pageable);

    Page<Subscription> findByCategoryId(Long id, Pageable pageable);

    @Override
    @Query(value = """
            select s.id               as subscription_id,
                   s.name             as subscription_name,
                   s.price            as subscription_price,
                   s.name             as subscription_name,
                   s.image_url        as subscription_image_url,
                   s.description      as subscription_description,
                   o.id               as owner_id,
                   o.name             as owner_name,
                   u.id               as user_id,
                   u.email            as user_email,
                   u.login            as user_login,
                   u.password         as user_password,
                   b.id               as billing_account_id,
                   b.balance          as billing_account_balance,
                   b.cvv              as billing_account_cvv,
                   b.number           as billing_account_number
            from subscriptions s
                     inner join owners o on o.id = s.owner_id
                     inner join users u on u.id = o.user_id
                     left join billing_accounts b on b.id = o.billing_account_id
            where s.id in (:ids)
                                                            """,
            resultSetExtractorRef = "allSubscriptionsByIdExtractor")
    Iterable<Subscription> findAllById(@Param("ids") Iterable<Long> ids);
}
