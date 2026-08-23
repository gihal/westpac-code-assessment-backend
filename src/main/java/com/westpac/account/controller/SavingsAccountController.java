package com.westpac.account.controller;

import com.westpac.account.api.SavingsAccountsApi;
import com.westpac.account.api.model.CreateSavingsAccountRequest;
import com.westpac.account.api.model.SavingsAccountResponse;
import com.westpac.account.domain.SavingsAccount;
import com.westpac.account.mapper.SavingsAccountMapper;
import com.westpac.account.service.SavingsAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SavingsAccountController implements SavingsAccountsApi {

    private final SavingsAccountService savingsAccountService;
    private final SavingsAccountMapper savingsAccountMapper;

    @Override
    public ResponseEntity<SavingsAccountResponse> createSavingsAccount(
            CreateSavingsAccountRequest request) {

        SavingsAccount savingsAccount = savingsAccountMapper.toSavingsAccount(request);
        SavingsAccount account = savingsAccountService.createAccount(savingsAccount);
        SavingsAccountResponse response =
                savingsAccountMapper.toResponse(account);

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @Override
    public ResponseEntity<SavingsAccountResponse> getSavingsAccount(String accountNumber) {
        SavingsAccount account = savingsAccountService.getAccount(accountNumber);
        SavingsAccountResponse response = savingsAccountMapper.toResponse(account);
        return ResponseEntity
                .status(200)
                .body(response);
    }
}
