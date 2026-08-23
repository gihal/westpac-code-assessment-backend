package com.westpac.account.component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountNumberGeneratorTest {

    @Test
    void generate() {
        AccountNumberGenerator generator = new AccountNumberGenerator();
        String accountNumber = generator.generate();
        assertNotNull(accountNumber);
    }
}