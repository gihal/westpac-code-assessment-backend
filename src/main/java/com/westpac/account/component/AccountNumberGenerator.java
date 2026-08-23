package com.westpac.account.component;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a random account number.
     * @return a random account number
     */
    public String generate() {
        long number = 1_000_000_000L
                + RANDOM.nextLong(9_000_000_000L);

        return Long.toString(number);
    }
}