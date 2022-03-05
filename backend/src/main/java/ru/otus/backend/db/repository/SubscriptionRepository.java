package ru.otus.backend.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Iterable<Subscription> findAll(Pageable pageable);

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

    Page<Subscription> findByNameContaining(String name, Pageable pageable); //%name% т.е. ищем имя которое содержит наш запрос

    Page<Subscription> findByCategoryId(Long id, Pageable pageable);
//    @Query(value = "SELECT * FROM subscription WHERE subscription.name = ?1", nativeQuery = true)
//    Iterable<Subscription>test(String name);
}
