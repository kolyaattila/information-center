package com.information.center.accountservice.service;

import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountRequest findByUsername(String username);

    AccountRequest save(CreateAccountRequest createAccountRequest);

    AccountRequest updateAccount(AccountRequest account);
}
