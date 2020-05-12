package com.information.center.authservice.controller;

import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.model.UserRoleRequest;
import com.information.center.authservice.service.UserService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import model.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private static final String USERNAME = "username";
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @Captor
    ArgumentCaptor<String> argumentCaptor;
    private UserRequest userRequest;
    private UserRoleRequest userRole;
    private PasswordUpdateRequest passwordUpdateRequest;
    private ErrorResponse errorResponseWithoutErrors;

    @Before
    public void setUp() {
        userRole = UserRoleRequest.builder().build();
        userRequest = UserRequest.builder().build();
        passwordUpdateRequest = PasswordUpdateRequest.builder().build();
        errorResponseWithoutErrors = ErrorResponse.builder()
                .message(Collections.emptyList())
                .build();
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
    public void assignRoleToUser_expectedRoleAssignedToUser() {
        when(userService.assignRolesToUser(userRole)).thenReturn(errorResponseWithoutErrors);
        ErrorResponse errorResponse = userController.assignRolesToUser(userRole);

        assertFalse(errorResponse.hasErrors());
    }

    @Test(expected = BadRequest.class)
    public void assignRoleToUser_expectedInsertFailedException() {
        when(userService.assignRolesToUser(userRole)).thenThrow(InsertFailedException.class);
        userController.assignRolesToUser(userRole);
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

    @Test
    public void deleteUser() {
      userController.deleteUser(USERNAME);
      verify(userService).deleteUser(argumentCaptor.capture());
        assertEquals(USERNAME, argumentCaptor.getValue());
    }
}
