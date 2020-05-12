package com.information.center.authservice.controller;

import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.service.RoleService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions;
import exception.ServiceExceptions.InconsistentDataException;
import model.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {

    private static final String ROLE_NAME = "roleName";
    @InjectMocks
    private RoleController roleController;
    @Mock
    private RoleService roleService;
    @Captor
    ArgumentCaptor<String> argumentCaptor;
    private RoleRequest roleRequest;
    private ErrorResponse errorResponseWithErrors;
    private ErrorResponse errorResponseWithoutErrors;

    @Before
    public void setUp() {
        roleRequest = RoleRequest.builder().build();
        errorResponseWithErrors = ErrorResponse.builder()
                .message(Collections.singletonList("Error message"))
                .build();
        errorResponseWithoutErrors = ErrorResponse.builder()
                .message(Collections.emptyList())
                .build();
    }

    @Test
    public void createRole_expectErrors() {
        when(roleService.saveRole(roleRequest)).thenReturn(errorResponseWithErrors);

        ErrorResponse response = roleController.createRole(roleRequest);

        assertTrue(response.hasErrors());
    }

    @Test(expected = BadRequest.class)
    public void createRole_expectBadRequest() {
        when(roleService.saveRole(roleRequest)).thenThrow(InconsistentDataException.class);

        roleController.createRole(roleRequest);
    }

    @Test
    public void updateRole_expectResponseWithoutErrors() {
        when(roleService.updateRole(roleRequest)).thenReturn(errorResponseWithoutErrors);

        ErrorResponse response = roleController.updateRole(roleRequest);

        assertFalse(response.hasErrors());
    }

    @Test(expected = BadRequest.class)
    public void updateRole_expectBadRequest() {
        when(roleService.updateRole(roleRequest)).thenThrow(InconsistentDataException.class);

        roleController.updateRole(roleRequest);
    }

    @Test
    public void deleteRole_expectResponseWithoutErrors() {
        roleController.deleteRole(ROLE_NAME);
        verify(roleService).deleteRole(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), ROLE_NAME);
    }
}
