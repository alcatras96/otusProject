package ru.otus.backend.db.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.backend.db.entity.BillingAccount;

@Repository
public interface BillingAccountRepository extends CrudRepository<BillingAccount, Long> {

    @Modifying
    @Query("update billing_accounts set balance = balance + :moneyToAdd where id = :id")
    void addMonetToBillingAccount(@Param("id") Long id, @Param("moneyToAdd") Integer moneyToAdd);
}
