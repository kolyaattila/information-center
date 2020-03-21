package com.information.center.accountservice.service;

import com.information.center.accountservice.entity.Account;
import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

  AccountRequest findByName(String username);

  AccountRequest saveChanges(CreateAccountRequest createAccountRequest);

}
