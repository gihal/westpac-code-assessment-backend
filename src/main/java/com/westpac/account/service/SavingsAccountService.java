package com.westpac.account.service;

import com.westpac.account.component.AccountNumberGenerator;
import com.westpac.account.component.OffensiveNicknamePolicy;
import com.westpac.account.domain.SavingsAccount;
import com.westpac.account.exception.SavingsAccountException;
import com.westpac.account.repository.SavingsAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Service
public class SavingsAccountService {

    private static final Logger log =
            LoggerFactory.getLogger(SavingsAccountService.class);

    private static final int MAX_ACCOUNTS_PER_CUSTOMER = 5;

    private final SavingsAccountRepository repository;
    private final OffensiveNicknamePolicy offensiveNicknamePolicy;
    private final AccountNumberGenerator accountNumberGenerator;

    public SavingsAccountService(
            SavingsAccountRepository repository,
            OffensiveNicknamePolicy offensiveNicknamePolicy,
            AccountNumberGenerator accountNumberGenerator
    ) {
        this.repository = repository;
        this.offensiveNicknamePolicy = offensiveNicknamePolicy;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    /**
     * Creates a new savings account for a customer.
     * @param customerName
     * @param accountNickName
     * @return the newly created savings account
     */
    @Transactional
    public SavingsAccount createAccount(SavingsAccount account) {


        validateNickname(account.getAccountNickName());

        // Prevent concurrent requests for the same customer
        // from creating more than five accounts.
        repository.lockCustomer(account.getCustomerKey());

        long accountCount = repository.countByCustomerKey(account.getCustomerKey());

        if (accountCount >= MAX_ACCOUNTS_PER_CUSTOMER) {
            log.warn("Savings account creation rejected: account limit reached");
            throw new SavingsAccountException(
                    SavingsAccountException.ERROR_ACCOUNT_LIMIT_EXCEEDED,
                    "A customer cannot have more than 5 savings accounts"
            );
        }

        return repository.save(account);
    }

    /**
     * Retrieves a savings account by its account number.
     * @param accountNumber the account number
     * @return the savings account
     */
    @Transactional(readOnly = true)
    public SavingsAccount getAccount(String accountNumber) {

        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    log.warn("Savings account retrieval failed: account not found");

                    return new SavingsAccountException(
                            SavingsAccountException.ERROR_ACCOUNT_NOT_FOUND,
                            "Savings account not found"
                    );
                });
    }

    /**
     * Validates the nickname for offensive terms.
     * @param accountNickName the nickname to validate
     */
    private void validateNickname(String accountNickName) {
        if (offensiveNicknamePolicy.isOffensive(accountNickName)) {
            log.warn("Savings account creation rejected: offensive nickname");
            throw new SavingsAccountException(
                    SavingsAccountException.ERROR_OFFENSIVE_NICKNAME,
                    "Account nickname contains an offensive term"
            );
        }
    }


}