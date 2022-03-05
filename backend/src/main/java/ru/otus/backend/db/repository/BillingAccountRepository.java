package ru.otus.backend.db.repository;

import ru.otus.backend.db.entity.BillingAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountRepository extends CrudRepository<BillingAccount, Long> {
}
