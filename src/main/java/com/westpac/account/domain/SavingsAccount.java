package com.westpac.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "savings_account")
public class SavingsAccount {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "customer_key", nullable = false, length = 100)
    private String customerKey;

    @Column(name = "account_nick_name", length = 30)
    private String accountNickName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected SavingsAccount() {
        // Required by JPA
    }

    public SavingsAccount(
            UUID id,
            String accountNumber,
            String customerName,
            String customerKey,
            String accountNickName,
            Instant createdAt
    ) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.customerKey = customerKey;
        this.accountNickName = accountNickName;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public String getAccountNickName() {
        return accountNickName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}