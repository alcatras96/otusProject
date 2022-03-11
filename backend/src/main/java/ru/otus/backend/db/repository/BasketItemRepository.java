package ru.otus.backend.db.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.BasketItem;

import java.util.List;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {

    @Query(value = """
            select b.id          as basket_item_id,
                   b.customer_id as basket_item_customer_id,
                   b.quantity    as basket_item_quantity,
                   s.id          as subscription_id,
                   s.name        as subscription_name,
                   s.price       as subscription_price,
                   s.name        as subscription_name,
                   s.image_url   as subscription_image_url,
                   s.description as subscription_description
            from basket_items b
                     inner join subscriptions s on s.id = b.subscription_id
            where b.customer_id = :customer_id
                                                        """,
            resultSetExtractorRef = "basketItemsByCustomerIdExtractor")
    Iterable<BasketItem> findByCustomerId(@Param("customer_id") Long id);

    Long countByCustomerId(Long id);

    @Modifying
    @Query(value = "delete from basket_items b where b.customer_id = :customer_id")
    void deleteBasketItemsByCustomerId(@Param("customer_id") Long customerId);
}
