package com.information.center.authservice.controller;

import com.information.center.authservice.model.RoleRequest;
import javax.validation.Valid;
import model.ErrorResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('ADMIN')")
public interface RoleEndpoint {

  @PostMapping
  ErrorResponse createRole(@Valid @RequestBody RoleRequest roleRequest);

  @PutMapping
  ErrorResponse updateRole(@Valid @RequestBody RoleRequest roleRequest);
}
