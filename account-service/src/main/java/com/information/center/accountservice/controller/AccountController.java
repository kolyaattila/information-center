package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import com.information.center.accountservice.service.AccountService;
import exception.RestExceptions.BadRequest;
import exception.RestExceptions.ServiceUnavailable;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.ServiceUnavailableException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController implements AccountEndpoint {

  private final AccountService accountService;

  @Override
  public AccountRequest getAccountByUsername(@PathVariable String name) {
    try {
      return accountService.findByUsername(name);
    } catch (InconsistentDataException e) {
      throw new BadRequest(e.getMessage());
    }
  }

  @Override
  public AccountRequest saveCurrentAccount(@Valid @RequestBody CreateAccountRequest account) {
    try {
      return accountService.save(account);
    } catch (InsertFailedException | InconsistentDataException e) {
      throw new BadRequest(e.getMessage());
    } catch (ServiceUnavailableException e) {
      throw new ServiceUnavailable(e.getMessage());
    }
  }
}
