package com.information.center.authservice.service;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import com.information.center.authservice.convert.UserConverter;
import com.information.center.authservice.entity.User;
import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.repository.UserRepository;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

  private final UserConverter userConverter;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void createUser(UserRequest userRequest) {
    checkUniqueUsername(userRequest);
    encryptPassword(userRequest);
    try {
      userRepository.save(userConverter.toUser(userRequest));
    } catch (Exception e) {
      throw new InsertFailedException("Error inserting user");
    }
  }

  public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
    encryptPasswords(passwordUpdateRequest);
    checkIfPasswordsAreDifferent(passwordUpdateRequest);
    User user = getUser(passwordUpdateRequest);
    user.setPassword(passwordUpdateRequest.getPassword());
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

  private User getUser(PasswordUpdateRequest passwordUpdateRequest) {
    return userRepository.findByUsernameAndPassword(passwordUpdateRequest.getUsername(),
        passwordUpdateRequest.getOldPassword()).orElseGet(() -> {
      throw new InconsistentDataException("Wrong password");
    });
  }

  private void encryptPassword(UserRequest userRequest) {
    userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
  }

  private void encryptPasswords(PasswordUpdateRequest passwordUpdateRequest) {
    passwordUpdateRequest.setPassword(passwordEncoder.encode(passwordUpdateRequest.getPassword()));
    passwordUpdateRequest
        .setOldPassword(passwordEncoder.encode(passwordUpdateRequest.getOldPassword()));
  }

  private void checkUniqueUsername(UserRequest userRequest) {
    if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
      throw new InconsistentDataException("Username already exist");
    }
  }
}
