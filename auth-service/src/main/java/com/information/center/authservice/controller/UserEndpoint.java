package com.information.center.authservice.controller;

import com.information.center.authservice.model.PasswordUpdateRequest;
import com.information.center.authservice.model.UserRequest;
import com.information.center.authservice.model.UserRoleRequest;
import jdk.nashorn.internal.runtime.logging.Logger;
import model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public interface UserEndpoint {

    @PostMapping
    ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest);

    @PostMapping
    ErrorResponse assignRolesToUser(@Valid @RequestBody UserRoleRequest userRoleRequest);

    @GetMapping("/{username}")
    ResponseEntity<?> deleteUser(@PathVariable("username") String username);

    @PutMapping
    ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest);

    @GetMapping("/current")
    Principal getUser(Principal principal);
}
