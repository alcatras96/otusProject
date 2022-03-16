package ru.otus.backend.db.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.Owner;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends PagingAndSortingRepository<Owner, Long> {

    @Query(value = """
            select o.id       as owner_id,
                   o.name     as owner_name,
                   u.id       as user_id,
                   u.email    as user_email,
                   u.login    as user_login,
                   u.password as user_password,
                   b.id       as billing_account_id,
                   b.balance  as billing_account_balance,
                   b.cvv      as billing_account_cvv,
                   b.number   as billing_account_number,
                   r.id       as role_id,
                   r.name     as role_name
            from owners o
                     inner join users u
                                on o.user_id = u.id
                     left join billing_accounts b
                                on o.billing_account_id = b.id
                     inner join roles r on r.id = u.role_id
            where o.id = :id
                                                            """,
            resultSetExtractorRef = "ownerExtractor")
    Optional<Owner> findById(@Param("id") Long id);

    @Query(value = """
            select o.id       as owner_id,
                   o.name     as owner_name,
                   u.id       as user_id,
                   u.email    as user_email,
                   u.login    as user_login,
                   u.password as user_password,
                   b.id       as billing_account_id,
                   b.balance  as billing_account_balance,
                   b.cvv      as billing_account_cvv,
                   b.number   as billing_account_number,
                   r.id       as role_id,
                   r.name     as role_name
            from owners o
                     inner join users u
                                on o.user_id = u.id
                     left join billing_accounts b
                                on o.billing_account_id = b.id
                     inner join roles r on r.id = u.role_id
            where o.user_id = :user_id
                                                            """,
            resultSetExtractorRef = "ownerExtractor")
    Optional<Owner> findByUserId(@Param("user_id") Long id);

    @Override
    @Query(value = """
            select o.id       as owner_id,
                   o.name     as owner_name,
                   u.id       as user_id,
                   u.email    as user_email,
                   u.login    as user_login,
                   u.password as user_password,
                   b.id       as billing_account_id,
                   b.balance  as billing_account_balance,
                   b.cvv      as billing_account_cvv,
                   b.number   as billing_account_number,
                   r.id       as role_id,
                   r.name     as role_name
            from owners o
                     inner join users u
                                on o.user_id = u.id
                     left join billing_accounts b
                                on o.billing_account_id = b.id
                     inner join roles r on r.id = u.role_id
                                                    """,
            resultSetExtractorRef = "allOwnersExtractor")
    List<Owner> findAll();
}
