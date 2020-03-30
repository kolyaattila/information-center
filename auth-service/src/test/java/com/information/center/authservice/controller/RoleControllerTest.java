package com.information.center.authservice.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.service.RoleService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import java.util.Collections;
import model.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {

  @InjectMocks
  private RoleController roleController;
  @Mock
  private RoleService roleService;
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

    ErrorResponse response = roleController.updateRole(roleRequest);

    assertFalse(response.hasErrors());
  }
}
