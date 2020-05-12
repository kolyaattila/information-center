package com.information.center.accountservice.controller;


import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import com.information.center.accountservice.service.AccountService;
import exception.RestExceptions.BadRequest;
import exception.RestExceptions.ServiceUnavailable;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.ServiceUnavailableException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sun.security.acl.PrincipalImpl;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {


    private static final String USERNAME = "username";
    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;
    private AccountRequest accountRequest;
    private CreateAccountRequest createAccountRequest;
    @Captor
    private ArgumentCaptor<String> usernameArgument;

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

        AccountRequest response = accountController.getCurrentAccount(new PrincipalImpl("name"));

        assertEquals(accountRequest, response);
    }

    @Test(expected = BadRequest.class)
    public void getAccountByUsername_expectBadRequest() {
        when(accountService.findByUsername("name")).thenThrow(InconsistentDataException.class);

        accountController.getCurrentAccount(new PrincipalImpl("name"));
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

    @Test
    public void deleteAccount_expectAccountDeleted() {
        accountService.delete(USERNAME);
        verify(accountService).delete(usernameArgument.capture());
        assertEquals(USERNAME, usernameArgument.getValue());
    }

    @Test(expected = BadRequest.class)
    public void deleteAccount_expectInsertFailedException() {
        doThrow(new InsertFailedException()).when(accountService).delete(USERNAME);
        accountController.deleteAccount(USERNAME);
    }

    @Test(expected = ServiceUnavailable.class)
    public void deleteAccount_expectServiceUnavailableException() {
        doThrow(new ServiceUnavailable()).when(accountService).delete(USERNAME);
        accountService.delete(USERNAME);
    }
}
