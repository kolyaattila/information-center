package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AccountEndpoint {

  @GetMapping("/")
  AccountRequest getCurrentAccount(Principal principal);

  @PostMapping("/")
  AccountRequest saveCurrentAccount(@Valid @RequestBody CreateAccountRequest account);

}
