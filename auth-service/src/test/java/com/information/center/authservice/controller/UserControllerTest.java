package com.information.center.authservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.service.UserService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.security.Principal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserController userController;
  private UserRequest userRequest;
  private PasswordUpdateRequest passwordUpdateRequest;

  @Before
  public void setUp() {
    userRequest = UserRequest.builder().build();
    passwordUpdateRequest = PasswordUpdateRequest.builder().build();
  }

  @Test
  public void createUser_expectUserToBeCreated() {
    ResponseEntity<?> response = userController.createUser(userRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test(expected = BadRequest.class)
  public void createUser_expectBadRequest() {
    doThrow(InconsistentDataException.class).when(userService).createUser(userRequest);

    userController.createUser(userRequest);
  }

  @Test
  public void updatePassword_expectPasswordToBeUpdated() {
    ResponseEntity<?> response = userController.updatePassword(passwordUpdateRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test(expected = BadRequest.class)
  public void updatePassword_expectBadRequest() {
    doThrow(InsertFailedException.class).when(userService).updatePassword(passwordUpdateRequest);

    userController.updatePassword(passwordUpdateRequest);
  }

  @Test
  public void getUser() {
    Principal principal = mock(Principal.class);
    Principal response = userController.getUser(principal);
    assertEquals(principal, response);
  }

}
