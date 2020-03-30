package com.information.center.accountservice.service;

import com.information.center.accountservice.client.AuthServiceClient;
import com.information.center.accountservice.converter.AccountConverter;
import com.information.center.accountservice.entity.AccountEntity;
import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import com.information.center.accountservice.repository.AccountRepository;
import exception.ServiceExceptions;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
  private final AccountConverter accountConverter;
  private final AccountRepository accountRepository;
  private final AuthServiceClient authServiceClient;


  @Override
  public AccountRequest findByUsername(String username) {
    AccountEntity account = accountRepository.findByUsername(username).orElseGet(() -> {
      throw new InconsistentDataException("Account not found");
    });
    return accountConverter.toAccountRequest(account);
  }

  @Override
  public AccountRequest save(CreateAccountRequest createAccountRequest) {
    AccountEntity account;
    if (!accountRepository.findByUsername(createAccountRequest.getUsername()).isPresent()) {
      account = saveAccount(createAccountRequest);
    } else {
      throw new ServiceExceptions.InconsistentDataException(
          "Account already exist with this username");
    }
    return accountConverter.toAccountRequest(account);
  }

  /**
   * The createUser can succeed and saveAccount can fail, that means you will have the user created
   * but the account not. One solution would be to save a default account with user's username
   */
  private AccountEntity saveAccount(CreateAccountRequest createAccountRequest) {
    createUser(createAccountRequest);
    AccountEntity account = accountConverter.toAccount(createAccountRequest);
    account.setUid(getUid());
    try {
      return accountRepository.save(account);
    } catch (Exception e) {
      saveDefaultAccountWithUserName(account);
      throw new InsertFailedException("Error inserting account");
    }
  }

  private void saveDefaultAccountWithUserName(AccountEntity account) {
    AccountEntity defaultAccount = new AccountEntity();
    defaultAccount.setUsername(account.getUsername());
    defaultAccount.setUid(account.getUid());
    accountRepository.save(defaultAccount);
  }

  private void createUser(CreateAccountRequest createAccountRequest) {
    ResponseEntity<?> response = authServiceClient.createUser(createAccountRequest);
    LOGGER.warn("Auth-service response with {}", response);
    if (!response.getStatusCode().equals(HttpStatus.OK)) {
      throw new ServiceExceptions.ServiceUnavailableException("Service unavailable");
    }
  }

  private AccountEntity updateAccount(CreateAccountRequest createAccountRequest, String username) {
    AccountEntity account = accountConverter.toAccount(createAccountRequest);
    copyPersistentData(account, username);
    try {
      return accountRepository.save(account);
    } catch (Exception e) {
      throw new InsertFailedException("Error update account");
    }
  }

  private void copyPersistentData(AccountEntity account, String username) {
    AccountEntity oldAccount = getAccount(username);

    account.setUid(oldAccount.getUid());
    account.setCreated(oldAccount.getCreated());
    account.setId(oldAccount.getId());
    account.setUsername(oldAccount.getUsername());
  }

  private AccountEntity getAccount(String uid) {
    return accountRepository.findByUid(uid).orElseGet(() -> {
      throw new InconsistentDataException("Account not found");
    });
  }

  private String getUid() {
    UUID uuid = UUID.randomUUID();
    if (accountRepository.existsByUid(uuid.toString())) {
      return this.getUid();
    }
    return uuid.toString();
  }
}
