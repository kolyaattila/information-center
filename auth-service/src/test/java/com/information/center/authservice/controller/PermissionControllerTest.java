package com.information.center.authservice.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.information.center.authservice.model.PermissionRequest;
import com.information.center.authservice.service.PermissionService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.util.Collections;
import model.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class PermissionControllerTest {

  @InjectMocks
  private PermissionController permissionController;
  @Mock
  private PermissionService permissionService;
  private PermissionRequest permissionRequest;
  private ErrorResponse errorResponseWithErrors;
  private ErrorResponse errorResponseWithoutErrors;

  @Before
  public void setUp() {
    permissionRequest = PermissionRequest.builder().build();
    errorResponseWithErrors = ErrorResponse.builder()
        .message(Collections.singletonList("Error message"))
        .build();
    errorResponseWithoutErrors = ErrorResponse.builder()
        .message(Collections.emptyList())
        .build();
  }

  @Test
  public void createPermission_expectErrors() {
    when(permissionService.createPermission(permissionRequest)).thenReturn(errorResponseWithErrors);

    ErrorResponse response = permissionController.createPermission(permissionRequest);

    assertTrue(response.hasErrors());
  }

  @Test
  public void updatePermission_withoutErrors() {
    when(permissionService.updatePermission(permissionRequest))
        .thenReturn(errorResponseWithoutErrors);

    ErrorResponse response = permissionController.updatePermission(permissionRequest);

    assertFalse(response.hasErrors());
  }

  @Test
  public void deletePermission_withoutErrors() {
    ResponseEntity<?> response = permissionController.deletePermission(permissionRequest);

    assertThat(response.getStatusCode(), is(HttpStatus.OK));
  }

  @Test(expected = BadRequest.class)
  public void deletePermission_expectBadRequest() {
    doThrow(InsertFailedException.class).when(permissionService)
        .deletePermission(permissionRequest);

    permissionController.deletePermission(permissionRequest);
  }

  @Test(expected = BadRequest.class)
  public void updatePermission_expectBadRequest() {
    when(permissionService.updatePermission(permissionRequest))
        .thenThrow(InconsistentDataException.class);

    permissionController.updatePermission(permissionRequest);
  }

  @Test(expected = BadRequest.class)
  public void createPermission_expectBadRequest() {
    when(permissionService.createPermission(permissionRequest))
        .thenThrow(InsertFailedException.class);

    permissionController.createPermission(permissionRequest);
  }

}
