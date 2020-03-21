package com.information.center.authservice.controller;

import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.service.UserService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserEndpoint {

  private final UserService userService;

  @Override
  public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
    try {
      userService.createUser(userRequest);
    } catch (InconsistentDataException | InsertFailedException e) {
      throw new BadRequest(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> updatePassword(
      @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
    try {
      userService.updatePassword(passwordUpdateRequest);
    } catch (InconsistentDataException | InsertFailedException e) {
      throw new BadRequest(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public Principal getUser(Principal principal) {
    return principal;
  }

}
