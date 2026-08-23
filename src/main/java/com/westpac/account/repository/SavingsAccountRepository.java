package com.westpac.account.repository;

import com.westpac.account.domain.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, UUID> {

    /**
     * Finds a savings account by account number.
     * @param accountNumber the account number
     * @return 1 or 0
     */
    Optional<SavingsAccount> findByAccountNumber(String accountNumber);

    /**
     * Counts the number of savings accounts for a given customer.
     * @param customerKey the customer key
     * @return the number of savings accounts
     */
    long countByCustomerKey(String customerKey);

    /**
     * Locks the customer for read-only access.
     * @param customerKey the customer key
     */
    @Query(
            value = "SELECT pg_advisory_xact_lock(hashtext(:customerKey))",
            nativeQuery = true
    )
    void lockCustomer(@Param("customerKey") String customerKey);
}