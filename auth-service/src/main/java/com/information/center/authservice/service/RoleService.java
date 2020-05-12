package com.information.center.authservice.service;

import com.information.center.authservice.convert.RoleConverter;
import com.information.center.authservice.entity.PermissionEntity;
import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.repository.PermissionRepository;
import com.information.center.authservice.repository.RoleRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;
    private final PermissionRepository permissionRepository;

    private void checkIfRoleAlreadyExist(String name) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new InconsistentDataException("Role already exist");
        }
    }

    private RoleEntity getRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            throw new InconsistentDataException("Role not exist");
        });
    }

    private ErrorResponse setAllPermissions(RoleEntity roleEntity, List<String> permissionsName) {
        ErrorResponse errorResponse = ErrorResponse.builder().build();
        permissionsName.forEach(permissionName -> {
            Optional<PermissionEntity> permission = permissionRepository.findByName(permissionName);
            if (permission.isPresent())
                roleEntity.getPermissions().add(permission.get());
            else
                errorResponse.with(ErrorResponse.builder()
                        .message(Collections.singletonList("Permission " + permissionName + " not found")).build());
        });
        return errorResponse;
    }

    public ErrorResponse saveRole(RoleRequest roleRequest) {
        checkIfRoleAlreadyExist(roleRequest.getName());
        RoleEntity role = roleConverter.toRole(roleRequest);
        ErrorResponse errorResponse = setAllPermissions(role, roleRequest.getPermissions());
        try {
            roleRepository.save(role);
            return errorResponse;
        } catch (Exception e) {
            throw new InsertFailedException("Error inserting role");
        }
    }

    public ErrorResponse updateRole(RoleRequest roleRequest) {

        RoleEntity roleEntity = getRole(roleRequest.getName());
        RoleEntity role = roleConverter.toRole(roleRequest, roleEntity.getId());
        ErrorResponse errorResponse = setAllPermissions(role, roleRequest.getPermissions());
        try {
            roleRepository.save(role);
            return errorResponse;
        } catch (Exception e) {
            throw new InsertFailedException("Error inserting role");
        }
    }

    public void deleteRole(String name) {
        var persistentRole = getRole(name);
        roleRepository.delete(persistentRole);
    }
}
