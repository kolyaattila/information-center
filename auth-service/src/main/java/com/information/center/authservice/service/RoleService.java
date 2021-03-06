package com.information.center.authservice.service;

import com.information.center.authservice.convert.RoleConverter;
import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.model.RoleRequest;
import com.information.center.authservice.repository.RoleRepository;
import com.information.center.authservice.repository.UserRepository;
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
public class RoleService {

  private final RoleRepository roleRepository;
  private final RoleConverter roleConverter;
  private final UserRepository userRepository;


  public ErrorResponse saveRole(RoleRequest roleRequest) {
    checkIfRoleAlreadyExist(roleRequest);
    RoleEntity role = roleConverter.toRole(roleRequest);
    ErrorResponse errorResponse = setAllUsers(role, roleRequest);
    try {
      roleRepository.save(role);
      return errorResponse;
    } catch (Exception e) {
      throw new InsertFailedException("Error inserting role");
    }
  }

  public ErrorResponse updateRole(RoleRequest roleRequest) {
    RoleEntity role = getRole(roleRequest);
    role.setDescription(roleRequest.getDescription());
    ErrorResponse errorResponse = setAllUsers(role, roleRequest);
    try {
      roleRepository.save(role);
      return errorResponse;
    } catch (Exception e) {
      throw new InsertFailedException("Error inserting role");
    }
  }

  private ErrorResponse setAllUsers(RoleEntity role, RoleRequest roleRequest) {
    ErrorResponse errorResponse = ErrorResponse.builder().build();
    roleRequest.getUsernames().forEach(username -> {
      Optional<UserEntity> byUsername = userRepository.findByUsername(username);
      if (byUsername.isPresent()) {
        role.getUsers().add(byUsername.get());
      } else {
        errorResponse.with(ErrorResponse.builder()
            .message(Collections.singletonList("User " + username + " not found")).build());
      }
    });
    return errorResponse;
  }

  private void checkIfRoleAlreadyExist(RoleRequest roleRequest) {
    if (roleRepository.findByName(roleRequest.getName()).isPresent()) {
      throw new InconsistentDataException("Role already exist");
    }
  }

  private RoleEntity getRole(RoleRequest roleRequest) {
    return roleRepository.findByName(roleRequest.getName()).orElseGet(() -> {
      throw new InconsistentDataException("Role not exist");
    });
  }
}
