package ru.otus.backend.db.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = """
            select c.id       as customer_id,
                   c.name     as customer_name,
                   c.address  as customer_address,
                   u.id       as user_id,
                   u.email    as user_email,
                   u.login    as user_login,
                   u.password as user_password,
                   b.id       as billing_account_id,
                   b.balance  as billing_account_balance,
                   b.cvv      as billing_account_cvv,
                   b.number   as billing_account_number,
                   s.name     as status_name,
                   r.id       as role_id,
                   r.name     as role_name
            from customers c
                     inner join users u
                                on c.user_id = u.id
                     inner join billing_accounts b
                                on c.billing_account_id = b.id
                     inner join statuses s on c.status_id = s.id
                     inner join roles r on r.id = u.role_id
            where c.user_id = :user_id
                                                        """,
            resultSetExtractorRef = "customerByUserIdExtractor")
    Customer findByUserId(@Param("user_id") Long id);

    Iterable<Customer> findAll(Pageable pageable);
}