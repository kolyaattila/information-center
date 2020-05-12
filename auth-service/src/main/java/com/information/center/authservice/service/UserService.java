package com.information.center.authservice.service;

import com.information.center.authservice.convert.UserConverter;
import com.information.center.authservice.entity.RoleEntity;
import com.information.center.authservice.entity.UserEntity;
import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.model.UserRoleRequest;
import com.information.center.authservice.repository.RoleRepository;
import com.information.center.authservice.repository.UserRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import lombok.RequiredArgsConstructor;
import model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRequest userRequest) {
        checkUniqueUsername(userRequest);
        encryptPassword(userRequest);
        UserEntity user = userConverter.toUser(userRequest);
        user.setEnabled(true);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new InsertFailedException("Error inserting user");
        }
    }

    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        checkIfPasswordsAreDifferent(passwordUpdateRequest);
        UserEntity user = getUser(passwordUpdateRequest.getUsername());

        if (!passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())) {
            throw new InconsistentDataException("Incorect password");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new InsertFailedException("Error updating password");
        }
    }


    private void checkIfPasswordsAreDifferent(PasswordUpdateRequest passwordUpdateRequest) {
        if (isNotBlank(passwordUpdateRequest.getOldPassword()) &&
                passwordUpdateRequest.getOldPassword().equals(passwordUpdateRequest.getPassword())) {
            throw new InconsistentDataException("The passwords must be different");
        }
    }

    private UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            throw new InconsistentDataException("User not found: " + username);
        });
    }

    private void encryptPassword(UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }

    private void checkUniqueUsername(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new InconsistentDataException("Username already exist");
        }
    }

    private ErrorResponse setRoleToUser(UserEntity user, List<String> roles) {
        ErrorResponse errorResponse = new ErrorResponse();
        roles.forEach(roleName -> {
            Optional<RoleEntity> role = roleRepository.findByName(roleName);
            if (role.isPresent())
                user.getRoles().add(role.get());
            else {
                errorResponse.with(ErrorResponse.builder().message(Collections.singletonList("Role " + roleName + " not found")).build());
            }
        });
        return errorResponse;
    }

    public ErrorResponse assignRolesToUser(UserRoleRequest userRoleRequest) {
        UserEntity user = getUser(userRoleRequest.getUsername());
        ErrorResponse errorResponse = setRoleToUser(user, userRoleRequest.getRoles());
        try {
            userRepository.save(user);
            return errorResponse;
        } catch (Exception e) {
            throw new InsertFailedException("Error inserting user");
        }
    }

    public void deleteUser(String username) {
        UserEntity user = getUser(username);
        userRepository.delete(user);
    }
}
