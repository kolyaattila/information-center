package com.information.center.accountservice.controller;


import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import com.information.center.accountservice.service.AccountService;
import exception.RestExceptions.BadRequest;
import exception.RestExceptions.ServiceUnavailable;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.ServiceUnavailableException;
import org.apache.http.auth.BasicUserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {


    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;
    private AccountRequest accountRequest;
    private CreateAccountRequest createAccountRequest;

    @Before
    public void setUp() {
        accountRequest = AccountRequest.builder()
                .birthday(new Date())
                .firstName("firstName")
                .lastName("lastName")
                .uid("uid")
                .username("username")
                .build();

        createAccountRequest = CreateAccountRequest.builder()
                .birthday(new Date())
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .username("username")
                .uid("uid")
                .build();
    }


    @Test
    public void getAccountByUsername_expectTheAccountRequest() {
        when(accountService.findByUsername("name")).thenReturn(accountRequest);

        AccountRequest response = accountController.getCurrentAccount(new BasicUserPrincipal("name"));

        assertEquals(accountRequest, response);
    }

    @Test(expected = BadRequest.class)
    public void getAccountByUsername_expectBadRequest() {
        when(accountService.findByUsername("name")).thenThrow(InconsistentDataException.class);

        accountController.getCurrentAccount(new BasicUserPrincipal("name"));
    }

    @Test
    public void saveCurrentAccount_expectAccountRequest() {
        when(accountService.save(createAccountRequest)).thenReturn(accountRequest);

        AccountRequest response = accountController.saveCurrentAccount(createAccountRequest);

        assertEquals(accountRequest, response);
        assertEquals(accountRequest.getBirthday(), response.getBirthday());
        assertEquals(accountRequest.getFirstName(), response.getFirstName());
        assertEquals(accountRequest.getLastName(), response.getLastName());
        assertEquals(accountRequest.getPhoto(), response.getPhoto());
        assertEquals(accountRequest.getUid(), response.getUid());
        assertEquals(accountRequest.getUsername(), response.getUsername());
    }

    @Test(expected = ServiceUnavailable.class)
    public void saveCurrentAccount_expectServiceUnavailable() {
        when(accountService.save(createAccountRequest))
                .thenThrow(ServiceUnavailableException.class);

        accountController.saveCurrentAccount(createAccountRequest);
    }

    @Test(expected = BadRequest.class)
    public void saveCurrentAccount_expectBadRequest() {
        when(accountService.save(createAccountRequest)).thenThrow(InsertFailedException.class);

        accountController.saveCurrentAccount(createAccountRequest);
    }

    @Test
    public void updateAccount() {
        when(accountService.updateAccount(accountRequest)).thenReturn(accountRequest);

        AccountRequest response = accountController.updateAccount(accountRequest);

        assertEquals(response, accountRequest);
    }

    @Test(expected = BadRequest.class)
    public void updateAccountWhenServiceThrowInsertFailedException_expectBadRequest() {
        when(accountService.updateAccount(accountRequest)).thenThrow(InsertFailedException.class);

        accountController.updateAccount(accountRequest);
    }

    @Test(expected = BadRequest.class)
    public void updateAccountWhenServiceThrowInconsistentDataException_expectBadRequest() {
        when(accountService.updateAccount(accountRequest)).thenThrow(InconsistentDataException.class);

        accountController.updateAccount(accountRequest);
    }
}
