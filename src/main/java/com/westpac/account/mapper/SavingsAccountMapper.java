package com.westpac.account.mapper;

import com.westpac.account.api.model.CreateSavingsAccountRequest;
import com.westpac.account.api.model.SavingsAccountResponse;
import com.westpac.account.component.AccountNumberGenerator;
import com.westpac.account.domain.SavingsAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SavingsAccountMapper {

    private final AccountNumberGenerator accountNumberGenerator;

    /**
     * Maps a SavingsAccount to a SavingsAccountResponse.
     * @param account the account
     * @return the mapped response
     */
    public SavingsAccountResponse toResponse(SavingsAccount account) {

        return new SavingsAccountResponse()
                .accountNumber(account.getAccountNumber())
                .customerName(account.getCustomerName())
                .accountNickName(account.getAccountNickName());
    }

    /**
     * Maps a CreateSavingsAccountRequest to a SavingsAccount.
     * @param account the request
     * @return the mapped SavingsAccount
     */
    public SavingsAccount toSavingsAccount(CreateSavingsAccountRequest account) {
        String normalizedCustomerName = account.getCustomerName().trim();
        String customerKey = normalizeCustomerKey(normalizedCustomerName);

        return new SavingsAccount(
                UUID.randomUUID(),
                accountNumberGenerator.generate(),
                normalizedCustomerName,
                customerKey,
                normalizeNickname(account.getAccountNickName()),
                Instant.now()
        );
    }

    /**
     * Normalizes the customer key by converting it to lowercase and removing
     * @param customerName the customer name
     * @return the normalized customer key
     */
    private String normalizeCustomerKey(String customerName) {
        return customerName
                .trim()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ");
    }

    /**
     * Normalizes the nickname by trimming whitespace.
     * @param nickname the nickname
     * @return the normalized nickname
     */
    private String normalizeNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return null;
        }

        return nickname.trim();
    }
}
