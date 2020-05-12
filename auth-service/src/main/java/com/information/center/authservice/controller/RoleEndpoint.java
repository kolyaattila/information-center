package com.information.center.authservice.controller;

import com.information.center.authservice.model.RoleRequest;
import model.ErrorResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('ADMIN')")
public interface RoleEndpoint {

    @PostMapping
    ErrorResponse createRole(@Valid @RequestBody RoleRequest roleRequest);

    @PutMapping
    ErrorResponse updateRole(@Valid @RequestBody RoleRequest roleRequest);

    @DeleteMapping("/{name}")
    void deleteRole(@PathVariable("name") String name);
}
