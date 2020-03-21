package com.information.center.authservice.controller;

import com.information.center.authservice.model.PermissionRequest;
import javax.validation.Valid;
import model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@PreAuthorize("hasRole('ADMIN')")
public interface PermissionEndpoint {

  @PostMapping
  ErrorResponse createPermission(@Valid @RequestBody PermissionRequest permissionRequest);

  @PutMapping
  ErrorResponse updatePermission(@Valid @RequestBody PermissionRequest permissionRequest);

  @DeleteMapping
  ResponseEntity<?> deletePermission(@Valid @RequestBody PermissionRequest permissionRequest);

}
