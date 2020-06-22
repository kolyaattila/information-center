package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/account")
public interface AccountEndpoint {

    @GetMapping("/")
    AccountRequest getCurrentAccount(Principal principal);

    @PostMapping("/")
    AccountRequest saveCurrentAccount(@Valid @RequestBody CreateAccountRequest account);

    @PutMapping("/update")
    AccountRequest updateAccount(@Valid @RequestBody AccountRequest account);

}
