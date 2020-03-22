package com.information.center.authservice.controller;

import com.information.center.authservice.model.PermissionRequest;
import com.information.center.authservice.service.PermissionService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import lombok.RequiredArgsConstructor;
import model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionController implements PermissionEndpoint {

  private final PermissionService permissionService;

  @Override
  public ErrorResponse createPermission(PermissionRequest permissionRequest) {
    try {
      return permissionService.createPermission(permissionRequest);
    } catch (InconsistentDataException | InsertFailedException e) {
      throw new BadRequest(e.getMessage());
    }
  }

  @Override
  public ErrorResponse updatePermission(PermissionRequest permissionRequest) {
    try {
      return permissionService.updatePermission(permissionRequest);
    } catch (InconsistentDataException | InsertFailedException e) {
      throw new BadRequest(e.getMessage());
    }
  }

  @Override
  public ResponseEntity<?> deletePermission(PermissionRequest permissionRequest) {
    try {
      permissionService.deletePermission(permissionRequest);
    } catch (InconsistentDataException | InsertFailedException e) {
      throw new BadRequest(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
