package com.information.center.authservice.controller;

import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.service.RoleService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import lombok.RequiredArgsConstructor;
import model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController implements RoleEndpoint {

    private final RoleService roleService;

    @Override
    public ErrorResponse createRole(@Valid @RequestBody RoleRequest roleRequest) {
        try {
            return roleService.saveRole(roleRequest);
        } catch (InsertFailedException | InconsistentDataException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    @Override
    public ErrorResponse updateRole(@Valid @RequestBody RoleRequest roleRequest) {
        try {
            return roleService.updateRole(roleRequest);
        } catch (InsertFailedException | InconsistentDataException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    @Override
    public void deleteRole(@PathVariable("name") String name) {
            roleService.deleteRole(name);
    }
}
