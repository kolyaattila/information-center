package com.information.center.authservice.service;

import com.information.center.authservice.convert.RoleConverter;
import com.information.center.authservice.entity.Role;
import com.information.center.authservice.entity.User;
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
    Role role = roleConverter.toRole(roleRequest);
    ErrorResponse errorResponse = setAllUsers(role, roleRequest);
    try {
      roleRepository.save(role);
      return errorResponse;
    } catch (Exception e) {
      throw new InsertFailedException("Error inserting role");
    }
  }

  public ErrorResponse updateRole(RoleRequest roleRequest) {
    Role role = getRole(roleRequest);
    role.setDescription(roleRequest.getDescription());
    ErrorResponse errorResponse = setAllUsers(role, roleRequest);
    try {
      roleRepository.save(role);
      return errorResponse;
    } catch (Exception e) {
      throw new InsertFailedException("Error inserting role");
    }
  }

  private ErrorResponse setAllUsers(Role role, RoleRequest roleRequest) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(Collections.emptyList()).build();
    roleRequest.getUsernames().forEach(username -> {
      Optional<User> byUsername = userRepository.findByUsername(username);
      if (byUsername.isPresent()) {//dublicate user
        role.getUsers().add(byUsername.get());
      } else {
        errorResponse.getMessage().add("User " + username + " not found");
      }
    });
    return errorResponse;
  }

  private void checkIfRoleAlreadyExist(RoleRequest roleRequest) {
    if (roleRepository.findByName(roleRequest.getName()).isPresent()) {
      throw new InconsistentDataException("Role already exist");
    }
  }

  private Role getRole(RoleRequest roleRequest) {
    return roleRepository.findByName(roleRequest.getName()).orElseGet(() -> {
      throw new InconsistentDataException("Role not exist");
    });
  }
}
