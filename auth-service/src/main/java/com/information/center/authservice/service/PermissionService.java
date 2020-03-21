package com.information.center.authservice.service;

import com.information.center.authservice.convert.PermissionConverter;
import com.information.center.authservice.entity.Permission;
import com.information.center.authservice.entity.Role;
import com.information.center.authservice.model.PermissionRequest;
import com.information.center.authservice.repository.PermissionRepository;
import com.information.center.authservice.repository.RoleRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

  private final PermissionRepository permissionRepository;
  private final PermissionConverter permissionConverter;
  private final RoleRepository roleRepository;

  public ErrorResponse createPermission(PermissionRequest permissionRequest) {
    checkIfAlreadyExist(permissionRequest);
    Permission permission = permissionConverter.toPermission(permissionRequest);
    ErrorResponse errorResponse = setAllRoles(permission, permissionRequest);
    try {
      permissionRepository.save(permission);
      return errorResponse;
    } catch (Exception e) {
      throw new InsertFailedException("Error inserting permission");
    }
  }

  private ErrorResponse setAllRoles(Permission permission, PermissionRequest permissionRequest) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(Collections.emptyList()).build();
    permissionRequest.getRoles().forEach(role -> {
      Optional<Role> byName = roleRepository.findByName(role);
      if (byName.isPresent()) {
        permission.getRoleEntities().add(byName.get());
      } else {
        errorResponse.with(ErrorResponse.builder()
            .message(Collections.singletonList("Role " + role + " not found")).build());
      }
    });
    return errorResponse;
  }

  private void checkIfAlreadyExist(PermissionRequest permissionRequest) {
    if (permissionRepository.findByName(permissionRequest.getName()).isPresent()) {
      throw new InconsistentDataException("Permission not found");
    }

  }

  public ErrorResponse updatePermission(PermissionRequest permissionRequest) {
    Permission permission = getPermission(permissionRequest.getName());
    ErrorResponse errorResponse = setAllRoles(permission, permissionRequest);
    permission.setDescription(permissionRequest.getDescription());
    try {
      permissionRepository.save(permission);
      return errorResponse;
    } catch (Exception e) {
      throw new InsertFailedException("Error updating permission");
    }
  }

  private Permission getPermission(String permission) {
    return permissionRepository.findByName(permission).orElseGet(() -> {
      throw new InconsistentDataException("Permission not found");
    });
  }

  public void deletePermission(PermissionRequest permissionRequest) {
    Permission permission = getPermission(permissionRequest.getName());
    try {
      permissionRepository.delete(permission);
    } catch (Exception e) {
      throw new InconsistentDataException("Could not delete permission");
    }
  }
}
