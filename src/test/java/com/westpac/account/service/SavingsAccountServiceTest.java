package com.westpac.account.service;

import com.westpac.account.component.AccountNumberGenerator;
import com.westpac.account.component.OffensiveNicknamePolicy;
import com.westpac.account.domain.SavingsAccount;
import com.westpac.account.exception.SavingsAccountException;
import com.westpac.account.repository.SavingsAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SavingsAccountServiceTest {

    @Mock
    private SavingsAccountRepository repository;

    @Mock
    private OffensiveNicknamePolicy offensiveNicknamePolicy;

    @Mock
    private AccountNumberGenerator accountNumberGenerator;

    private SavingsAccountService service;

    @BeforeEach
    void setUp() {
        service = new SavingsAccountService(
                repository,
                offensiveNicknamePolicy,
                accountNumberGenerator
        );
    }

    @Test
    void createAccountWithValidData() {
        when(offensiveNicknamePolicy.isOffensive("Holiday Fund"))
                .thenReturn(false);

        when(repository.countByCustomerKey("gihal mapalagama"))
                .thenReturn(2L);

        when(repository.save(any(SavingsAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SavingsAccount account = new SavingsAccount(
                UUID.randomUUID(),
                "1234567890",
                "Gihal Mapalagama",
                "gihal mapalagama",
                "Holiday Fund",
                Instant.now()
        );

        SavingsAccount result = service.createAccount(account);

        assertThat(result.getCustomerName())
                .isEqualTo("Gihal Mapalagama");

        assertThat(result.getCustomerKey())
                .isEqualTo("gihal mapalagama");

        assertThat(result.getAccountNumber())
                .isEqualTo("1234567890");

        verify(repository).lockCustomer("gihal mapalagama");
        verify(repository).countByCustomerKey("gihal mapalagama");
        verify(repository).save(any(SavingsAccount.class));
    }


    @Test
    void getAccountTest() {
        SavingsAccount account = mock(SavingsAccount.class);

        when(repository.findByAccountNumber("1234567890"))
                .thenReturn(Optional.of(account));

        SavingsAccount result =
                service.getAccount("1234567890");

        assertThat(result).isSameAs(account);
    }

    //TODO: Add more tests here: Rainy day scenarios, Exceptions and edge cases


}