package com.information.center.accountservice.service;

import com.information.center.accountservice.client.AuthServiceClient;
import com.information.center.accountservice.converter.AccountConverter;
import com.information.center.accountservice.entity.AccountEntity;
import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import com.information.center.accountservice.repository.AccountRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.ServiceUnavailableException;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private static final String USERNAME = "username";
    private static final String LAST_NAME = "lastName";
    private static final String UID = "uid";
    private static final String FIRST_NAME = "firstName";
    private final ArgumentCaptor<AccountEntity> captor = ArgumentCaptor.forClass(AccountEntity.class);
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountConverter accountConverter;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AuthServiceClient authServiceClient;
    @Rule
    public final OutputCapture outputCapture = new OutputCapture();
    private AccountRequest accountRequest;
    private CreateAccountRequest createAccountRequest;
    private AccountEntity account;

    @Before
    public void setUp() {
        outputCapture.reset();
        accountRequest = AccountRequest.builder()
                .username(USERNAME)
                .lastName(LAST_NAME)
                .uid(UID)
                .firstName(FIRST_NAME)
                .birthday(new Date())
                .build();

        createAccountRequest = CreateAccountRequest.builder()
                .username(USERNAME)
                .lastName(LAST_NAME)
                .uid(UID)
                .firstName(FIRST_NAME)
                .birthday(new Date())
                .build();

        account = new AccountEntity();
        account.setUsername(USERNAME);
    }

    @Test
    public void findByUsername_whenUsernameFound_expectAccount() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.of(account));
        when(accountConverter.toAccountRequest(account)).thenReturn(accountRequest);

        AccountRequest response = accountService.findByUsername(USERNAME);

        assertThat(accountRequest, is(response));
    }

    @Test(expected = InconsistentDataException.class)
    public void findByUsername_whenUsernameNotFound_expectInconsistentDataException() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        accountService.findByUsername(USERNAME);

    }

    @Test
    public void save_expectToCreateUserAndAccount() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(authServiceClient.createUser(createAccountRequest))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(accountConverter.toAccount(createAccountRequest)).thenReturn(account);
        when(accountRepository.existsByUid(anyString())).thenReturn(true).thenReturn(false);
        when(accountRepository.save(account)).then(r -> r.getArgument(0));
        when(accountConverter.toAccountRequest(account)).thenReturn(accountRequest);

        AccountRequest response = accountService.save(createAccountRequest);

        outputCapture.expect(containsString(
                String.format("Auth-service response with %s", new ResponseEntity<>(HttpStatus.OK))));
        assertThat(accountRequest, is(response));
    }

    @Test(expected = InconsistentDataException.class)
    public void save_expectInconsistentDataException() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.of(account));

        accountService.save(createAccountRequest);
    }

    @Test(expected = ServiceUnavailableException.class)
    public void save_expectServiceUnavailableException() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(authServiceClient.createUser(createAccountRequest))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));

        accountService.save(createAccountRequest);

        outputCapture.expect(containsString(
                String.format("Auth-service response with %s",
                        new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE))));
    }

    @Test
    public void save_expectToCreateUserAndThrowExceptionForAccount() {
        when(accountRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(authServiceClient.createUser(createAccountRequest))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(accountConverter.toAccount(createAccountRequest)).thenReturn(account);
        when(accountRepository.existsByUid(anyString())).thenReturn(true).thenReturn(false);
        when(accountRepository.save(account)).thenThrow(HibernateException.class);

        try {
            accountService.save(createAccountRequest);
        } catch (Exception e) {
            assertTrue(e instanceof InsertFailedException);
            outputCapture.expect(containsString(
                    String.format("Auth-service response with %s", new ResponseEntity<>(HttpStatus.OK))));
            verify(accountRepository, times(2)).save(captor.capture());
            AccountEntity accountCaptor = captor.getValue();
            assertEquals(USERNAME, accountCaptor.getUsername());
            assertFalse(accountCaptor.getUid().isEmpty());
        }
    }

    @Test
    public void updateAccount() {
        ArgumentCaptor<AccountEntity> capture = ArgumentCaptor.forClass(AccountEntity.class);
        accountRequest.setFirstName("newFirstName");
        accountRequest.setLastName("newLastName");
        when(accountRepository.findByUid(UID)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).then(r -> r.getArgument(0));
        when(accountConverter.toAccountRequest(account)).thenReturn(accountRequest);

        AccountRequest response = accountService.updateAccount(accountRequest);

        verify(accountRepository).save(capture.capture());
        assertEquals(response, accountRequest);
        assertEquals("newFirstName", capture.getValue().getFirstName());
        assertEquals("newLastName", capture.getValue().getLastName());
    }


    @Test(expected = InconsistentDataException.class)
    public void updateAccountWhenAccountNotFound_expectInconsistentDataException() {
        when(accountRepository.findByUid(UID)).thenReturn(Optional.empty());

        accountService.updateAccount(accountRequest);
    }

    @Test(expected = InsertFailedException.class)
    public void updateAccountWhenAccountCanNotBeSaved_expectInsertFailedException() {
        when(accountRepository.findByUid(UID)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenThrow(HibernateException.class);

        accountService.updateAccount(accountRequest);
    }
}
