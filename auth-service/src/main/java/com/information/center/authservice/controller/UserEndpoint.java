package com.information.center.authservice.controller;

import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
//@PreAuthorize("#oauth2.hasScope('server')")
public interface UserEndpoint {

  @PostMapping
  ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest);

  @PutMapping
  ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest);

  @GetMapping("/current")
  Principal getUser(Principal principal);
}
